/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.bo.ArtistListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.CassandraConstants.MSL_CONTENT_TYPE;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * ArtistList response. This is a paginated query, so the initial query will select a page
 * of artists from a single table (based on the facets, if any, that were passed from the
 * caller).
 * 
 *  The much of the artist's data is read from the songs_albums_by_artist table. This includes
 *  the artist name, genre, similar artists, albums and songs. Some of this data is denormalized
 *  so it is only retrieved from the first of potentially multiple rows.
 *  
 *  The artists community rating is retrieved from the average_ratings table. 
 *
 *  The artists user rating is retrieved from the user_data_by_user table. 
 *
 * @author billschwanitz
 */
public class ArtistListQuery {
	/*
	 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
	 * ArtistList response.
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
	 * @return the ArtistList instance with all the info for the requested page
	 */
	public static ArtistListBo get(final Session session, final UUID pagingStateUuid, final Integer items, final String facets, final UUID userUuid) {
		ArtistListBo artistListBo = new ArtistListBo();

		// Retrieve the requested page of Artists
		new Paginator(CassandraConstants.MSL_CONTENT_TYPE.ARTIST, session, pagingStateUuid, items, facets).getPage(artistListBo);
		
		/*
		 *  Asynchronously query for the average and user ratings for each artist.
		 *  
		 *  NOTE: this could be done using CQL's IN keyword, but that leads to scalability issues. See the great discussion of this issue
		 *  here: https://lostechies.com/ryansvihla/2014/09/22/cassandra-query-patterns-not-using-the-in-query-for-multiple-partitions
		 */
		Set<ResultSetFuture> averageRatingFutures = fireAverageRatingQueries(session, artistListBo);
		Set<ResultSetFuture> userRatingFutures = null;
		if (null != userUuid) {
			userRatingFutures = fireUserRatingQueries(session, artistListBo, userUuid);
		}
		
		// Now wait for the asynchronous queries to complete and roll the data into the list
		processAverageRatingResults(session, artistListBo, averageRatingFutures);
		if (null != userUuid) {
			processUserRatingResults(session, artistListBo, userRatingFutures);
		}
		
		return artistListBo;
	}

	/*
	 * Query for average rating for each artist DAO in the list.
	 * Instead of using a single query with an IN clause on a partition key column, which can have scalability issues,
	 * we will fire of one asynchronous query for each artist in the list.
	 */
	private static Set<ResultSetFuture> fireAverageRatingQueries(final Session session, final ArtistListBo artistListBo) {
		PreparedStatement statement = session.prepare(QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
				.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker()))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType)));
		
		Set<ResultSetFuture> returnSet = new HashSet<ResultSetFuture>(artistListBo.getBoList().size());
		
		for (ArtistBo artistBo : artistListBo.getBoList()) {
			returnSet.add(session.executeAsync(statement.bind(artistBo.getArtistId())));
		}
		
		return returnSet;
	}

	/*
	 * Query for user's rating for each artist DAO in the list.
	 * 
	 * Instead of using a single query with an IN clause on a partition key column, which can have scalability issues,
	 * we will fire of one asynchronous query for each artist in the list.
	 */
	private static Set<ResultSetFuture> fireUserRatingQueries(final Session session, final ArtistListBo artistListBo, final UUID userUuid) {
		PreparedStatement statement = session.prepare(QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
				.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, userUuid))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, QueryBuilder.bindMarker())));

		Set<ResultSetFuture> returnSet = new HashSet<ResultSetFuture>(artistListBo.getBoList().size());
		
		for (ArtistBo artistBo : artistListBo.getBoList()) {
			returnSet.add(session.executeAsync(statement.bind(artistBo.getArtistId())));
		}
		
		return returnSet;
	}

	/*
	 * Process the results from the queries for average rating for each artist DAO in the list.
	 */
	private static void processAverageRatingResults(final Session session, final ArtistListBo artistListBo, final Set<ResultSetFuture> resultFutures) {
		for (ResultSetFuture future : resultFutures) {
			// Wait for the query to complete and map single result row to a DAO POJOs
			AverageRatingsDao averageRatingsDao = new MappingManager(session).mapper(AverageRatingsDao.class).map(future.getUninterruptibly()).one();
			
			if (null != averageRatingsDao) {
				// Find and update the matching ArtistBo
				for (ArtistBo artistBo : artistListBo.getBoList()) {
					if (artistBo.getArtistId().equals(averageRatingsDao.getContentId())) {
						artistBo.setAverageRating(averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating());
						break;
					}
				}
			}
		}
	}

	/*
	 * Process the results from the queries for user rating for each artist DAO in the list.
	 */
	private static void processUserRatingResults(final Session session, final ArtistListBo artistListBo, final Set<ResultSetFuture> resultFutures) {
		for (ResultSetFuture future : resultFutures) {
			// Wait for the query to complete and map single result row to a DAO POJOs
			UserDataByUserDao userDataByUserDao = new MappingManager(session).mapper(UserDataByUserDao.class).map(future.getUninterruptibly()).one();
			
			// Find and update the matching ArtistBo
			for (ArtistBo artistBo : artistListBo.getBoList()) {
				if (artistBo.getArtistId().equals(userDataByUserDao.getContentUuid())) {
					artistBo.setPersonalRating(userDataByUserDao.getRating());
					break;
				}
			}
		}
	}
}
