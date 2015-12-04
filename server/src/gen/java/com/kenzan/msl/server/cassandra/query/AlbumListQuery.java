/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.AlbumListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.PreparedStatementFactory;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * AlbumList response. This is a paginated query, so the initial query will select a page
 * of albums from a single table (based on the facets, if any, that were passed from the
 * caller).
 * 
 *  The much of the album's data is read from the songs_artist_by_album table. This includes
 *  the album name, album year, genre, artists and songs. Some of this data is denormalized
 *  so it is only retrieved from the first of potentially multiple rows.
 *  
 *  The album's community rating is retrieved from the average_ratings table. 
 *
 *  The album's user rating is retrieved from the user_data_by_user table. 
 *
 * @author billschwanitz
 */
public class AlbumListQuery {
	/*
	 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
	 * AlbumList response.
	 * 
	 * @param session			the Datastax session through which queries and mappings should be executed
	 * @param pagingStateUuid	Used for pagination control.
	 * 								To retrieve the first page, use <code>null</code>.
	 * 								To retrieve subsequent pages, use the
	 * 									<code>pagingState</code> that accompanied the
	 * 									previous page.
	 * @param items				Specifies the number of items to include in each page.
	 * 								This value is only necessary on the retrieval of the
	 * 								first page, and will be used for all subsequent
	 * 								pages.
	 * @param facets			Specifies a comma delimited list of search facet Ids
	 * 								to filter the results.
	 * 							Pass null or an empty string to not filter.
	 * @param userUuid		Specifies a user UUID identifying the currently logged-in
	 * 							user. Will be null for unauthenticated requests.
	 * 
	 * @return the AlbumList instance with all the info for the requested page
	 */
	public static AlbumListBo get(final Session session, final UUID pagingStateUuid, final Integer items, final String facets, final UUID userUuid) {
		AlbumListBo albumListBo = new AlbumListBo();

		// Retrieve the requested page of Albums
		new Paginator(CassandraConstants.MSL_CONTENT_TYPE.ALBUM, session, pagingStateUuid, items, facets).getPage(albumListBo);
		
		/*
		 *  Asynchronously query for the average and user ratings for each album.
		 *  
		 *  NOTE: this could be done using CQL's IN keyword, but that leads to scalability issues. See the great discussion of this issue
		 *  here: https://lostechies.com/ryansvihla/2014/09/22/cassandra-query-patterns-not-using-the-in-query-for-multiple-partitions
		 */
		Set<ResultSetFuture> averageRatingFutures = fireAverageRatingQueries(session, albumListBo);
		Set<ResultSetFuture> userRatingFutures = null;
		if (null != userUuid) {
			userRatingFutures = fireUserRatingQueries(session, albumListBo, userUuid);
		}
		
		// Now wait for the asynchronous queries to complete and roll the data into the list
		processAverageRatingResults(session, albumListBo, averageRatingFutures);
		if (null != userUuid) {
			processUserRatingResults(session, albumListBo, userRatingFutures);
		}
		
		return albumListBo;
	}

	/*
	 * Query for average rating for each album DAO in the list.
	 * Instead of using a single query with an IN clause on a partition key column, which can have scalability issues,
	 * we will fire of one asynchronous query for each album in the list.
	 */
	private static Set<ResultSetFuture> fireAverageRatingQueries(final Session session, final AlbumListBo albumListBo) {
		Set<ResultSetFuture> returnSet = new HashSet<ResultSetFuture>(albumListBo.getBoList().size());
		
		for (AlbumBo albumBo : albumListBo.getBoList()) {
			BoundStatement statement = PreparedStatementFactory.getInstance()
					.getPreparedStatement(session,PreparedStatementFactory.PreparedStatementId.ALBUM_AVERAGE_RATING_QUERY)
					.bind(albumBo.getAlbumId());

			returnSet.add(session.executeAsync(statement));
		}
		
		return returnSet;
	}

	/*
	 * Query for user's rating for each album DAO in the list.
	 * 
	 * Instead of using a single query with an IN clause on a partition key column, which can have scalability issues,
	 * we will fire of one asynchronous query for each album in the list.
	 */
	private static Set<ResultSetFuture> fireUserRatingQueries(final Session session, final AlbumListBo albumListBo, final UUID userUuid) {
		Set<ResultSetFuture> returnSet = new HashSet<ResultSetFuture>(albumListBo.getBoList().size());
		
		for (AlbumBo albumBo : albumListBo.getBoList()) {
			BoundStatement statement = PreparedStatementFactory.getInstance()
					.getPreparedStatement(session,PreparedStatementFactory.PreparedStatementId.ALBUM_USER_RATING_QUERY)
					.bind(userUuid)
					.bind(albumBo.getAlbumId());

			returnSet.add(session.executeAsync(statement));
		}
		
		return returnSet;
	}

	/*
	 * Process the results from the queries for average rating for each album DAO in the list.
	 */
	private static void processAverageRatingResults(final Session session, final AlbumListBo albumListBo, final Set<ResultSetFuture> resultFutures) {
		for (ResultSetFuture future : resultFutures) {
			// Wait for the query to complete and map single result row to a DAO POJOs
			AverageRatingsDao averageRatingsDao = new MappingManager(session).mapper(AverageRatingsDao.class).map(future.getUninterruptibly()).one();
			
			if (null != averageRatingsDao) {
				// Find and update the matching AlbumBo
				for (AlbumBo albumBo : albumListBo.getBoList()) {
					if (albumBo.getAlbumId().equals(averageRatingsDao.getContentId())) {
						albumBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
						break;
					}
				}
			}
		}
	}

	/*
	 * Process the results from the queries for user rating for each artist DAO in the list.
	 */
	private static void processUserRatingResults(final Session session, final AlbumListBo artistListBo, final Set<ResultSetFuture> resultFutures) {
		for (ResultSetFuture future : resultFutures) {
			// Wait for the query to complete and map single result row to a DAO POJOs
			UserDataByUserDao userDataByUserDao = new MappingManager(session).mapper(UserDataByUserDao.class).map(future.getUninterruptibly()).one();
			
			// Find and update the matching AlbumBo
			for (AlbumBo artistBo : artistListBo.getBoList()) {
				if (artistBo.getAlbumId().equals(userDataByUserDao.getContentUuid())) {
					artistBo.setPersonalRating(userDataByUserDao.getRating());
					break;
				}
			}
		}
	}
}
