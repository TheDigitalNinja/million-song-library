/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.CassandraConstants.MSL_CONTENT_TYPE;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * AlbumInfo response.
 * 
 *  Much of the album's data is read from the songs_artist_by_album table. This includes
 *  the album name, genre, similar albums, albums and songs. Some of this data is denormalized
 *  so it is only retrieved from the first of potentially multiple rows.
 *  
 *  The album's community rating is retrieved from the average_ratings table. 
 *
 *  The album's user rating is retrieved from the user_data_by_user table. 
 *
 * @author billschwanitz
 */
public class AlbumInfoQuery {
	/*
	 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
	 * AlbumInfo response.
	 * 
	 * @param session		the Datastax session through which queries and mappings should be executed
	 * @param userId		the UUID of the logged in user making the query (will be null if user not logged in)
	 * @param albumUuid	the UUID of the album to be retrieved
	 * 
	 * @return the AlbumInfo instance with all the info on the requested user
	 */
	public static AlbumBo get(final Session session, final UUID userUuid, final UUID albumUuid) {
		AlbumBo albumBo = new AlbumBo();
		
		/*
		 * Retrieve data from the songs_artist_by_album table
		 */
		
		// Build query to select all rows for the given album from the songs_artist_by_album table 
		Statement statement1 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_SONGS_ARTIST_BY_ALBUM)
				.where(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_ALBUM_ID, albumUuid));
		
		// Execute the query and map results to DAO POJOs
		Result<SongsArtistByAlbumDao> results1 = new MappingManager(session).mapper(SongsArtistByAlbumDao.class).map(session.execute(statement1));

		// Loop through the results rows building up the AlbumInfo
		boolean processedOneRow = false;
		for (SongsArtistByAlbumDao songsArtistByAlbumDao : results1) {
			if (false == processedOneRow) {
				albumBo.setAlbumId(songsArtistByAlbumDao.getAlbumId());
				albumBo.setAlbumName(songsArtistByAlbumDao.getAlbumName());
				albumBo.setArtistId(songsArtistByAlbumDao.getArtistId());
				albumBo.setArtistName(songsArtistByAlbumDao.getArtistName());
				
				if (songsArtistByAlbumDao.getArtistGenres() != null && songsArtistByAlbumDao.getArtistGenres().size()>0) {
					albumBo.setGenre(songsArtistByAlbumDao.getArtistGenres().iterator().next());
				}
				
				processedOneRow = true;
			}
			
			// Add the song ID from this DAO if it is not already in the list
			if (!albumBo.getSongsList().contains(songsArtistByAlbumDao.getSongId().toString())) {
				albumBo.getSongsList().add(songsArtistByAlbumDao.getSongId().toString());
			}
		}

		// If we didn't retrieved a DAO, then return null 
		if (!processedOneRow) {
			return null;
		}

		/*
		 * Retrieve data from the average_ratings table
		 */
		
		// Build query to select all rows for the given album from the songs_artist_by_album table 
		Statement statement2 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
				.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, albumUuid))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ALBUM.dbContentType));
		
		// Execute the query and map single result row to a DAO POJOs
		AverageRatingsDao averageRatingsDao = new MappingManager(session).mapper(AverageRatingsDao.class).map(session.execute(statement2)).one();
		
		// If we retrieved a DAO, then include that info into the AlbumInfo 
		if (null != averageRatingsDao) {
			albumBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
		}
		
		/*
		 * Retrieve data from the user_data_by_user table if a user UUID was passed
		 */
		
		if (userUuid != null) {
			// Build query to select all rows for the given album from the user_data_by_user table 
			Statement statement3 = QueryBuilder.select()
					.all()
					.from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
					.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, userUuid))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ALBUM.dbContentType))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, albumUuid));
			
			// Execute the query and map single result row to a DAO POJOs
			UserDataByUserDao userDataByUserDao = new MappingManager(session).mapper(UserDataByUserDao.class).map(session.execute(statement3)).one();

			// If we retrieved a DAO, then include that info into the AlbumInfo 
			if (null != userDataByUserDao) {
				albumBo.setPersonalRating(userDataByUserDao.getRating());
			}
		}
		
		return albumBo;
	}
}
