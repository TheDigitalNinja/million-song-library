/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.CassandraConstants.MSL_CONTENT_TYPE;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * ArtistInfo response.
 * 
 *  Much of the artist's data is read from the songs_albums_by_artist table. This includes
 *  the artist name, genre, similar artists, albums and songs. Some of this data is denormalized
 *  so it is only retrieved from the first of potentially multiple rows.
 *  
 *  The artist's community rating is retrieved from the average_ratings table. 
 *
 *  The artist's user rating is retrieved from the user_data_by_user table. 
 *
 * @author billschwanitz
 */
public class ArtistInfoQuery {
	/*
	 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
	 * ArtistInfo response.
	 * 
	 * @param session		the Datastax session through which queries and mappings should be executed
	 * @param userId		the UUID of the logged in user making the query (will be null if user not logged in)
	 * @param artistUuid	the UUID of the artist to be retrieved
	 * 
	 * @return the ArtistInfo instance with all the info on the requested user
	 */
	public static ArtistBo get(final Session session, final UUID userUuid, final UUID artistUuid) {
		ArtistBo artistBo = new ArtistBo();
		
		/*
		 * Retrieve data from the songs_albums_by_artist table
		 */
		
		// Build query to select all rows for the given artist from the songs_albums_by_artist table 
		Statement statement1 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_SONGS_ALBUMS_BY_ARTIST)
				.where(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_ARTIST_ID, artistUuid));
		
		// Execute the query and map results to DAO POJOs
		Result<SongsAlbumsByArtistDao> results1 = new MappingManager(session).mapper(SongsAlbumsByArtistDao.class).map(session.execute(statement1));

		// Loop through the results rows building up the ArtistInfo
		boolean processedOneRow = false;
		for (SongsAlbumsByArtistDao songsAlbumsByArtistDao : results1) {
			if (false == processedOneRow) {
				artistBo.setArtistId(songsAlbumsByArtistDao.getArtistId());
				artistBo.setArtistName(songsAlbumsByArtistDao.getArtistName());
				
				if (songsAlbumsByArtistDao.getArtistGenres() != null && songsAlbumsByArtistDao.getArtistGenres().size()>0) {
					artistBo.setGenre(songsAlbumsByArtistDao.getArtistGenres().iterator().next());
				}
				
				if (songsAlbumsByArtistDao.getSimilarArtists() != null) {
					for (UUID similarArtistUuid : songsAlbumsByArtistDao.getSimilarArtists().keySet()) {
						artistBo.getSimilarArtistsList().add(similarArtistUuid.toString());
					}
				}
				
				processedOneRow = true;
			}
			
			// Add the album ID from this DAO if it is not already in the list
			if (!artistBo.getAlbumsList().contains(songsAlbumsByArtistDao.getAlbumId().toString())) {
				artistBo.getAlbumsList().add(songsAlbumsByArtistDao.getAlbumId().toString());
			}
			
			// Add the song ID from this DAO if it is not already in the list
			if (!artistBo.getSongsList().contains(songsAlbumsByArtistDao.getSongId().toString())) {
				artistBo.getSongsList().add(songsAlbumsByArtistDao.getSongId().toString());
			}
		}
		
		/*
		 * Retrieve data from the average_ratings table
		 */
		
		// Build query to select all rows for the given artist from the songs_albums_by_artist table 
		Statement statement2 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
				.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, artistUuid))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType));
		
		// Execute the query and map single result row to a DAO POJOs
		AverageRatingsDao averageRatingsDao = new MappingManager(session).mapper(AverageRatingsDao.class).map(session.execute(statement2)).one();
		
		// If we retrieved a DAO, then include that info into the ArtistInfo 
		if (null != averageRatingsDao) {
			artistBo.setAverageRating(averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating());
		}
		
		/*
		 * Retrieve data from the user_data_by_user table if a user UUID was passed
		 */
		
		if (userUuid != null) {
			// Build query to select all rows for the given artist from the songs_albums_by_artist table 
			Statement statement3 = QueryBuilder.select()
					.all()
					.from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
					.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, userUuid))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.ARTIST.dbContentType))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, artistUuid));
			
			// Execute the query and map single result row to a DAO POJOs
			UserDataByUserDao userDataByUserDao = new MappingManager(session).mapper(UserDataByUserDao.class).map(session.execute(statement3)).one();

			// If we retrieved a DAO, then include that info into the ArtistInfo 
			if (null != userDataByUserDao) {
				artistBo.setPersonalRating(userDataByUserDao.getRating());
			}
		}
		
		return artistBo;
	}
}
