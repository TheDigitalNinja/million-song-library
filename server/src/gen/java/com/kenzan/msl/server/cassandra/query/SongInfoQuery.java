/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.CassandraConstants.MSL_CONTENT_TYPE;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.AlbumArtistBySongDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * SongInfo response.
 * 
 *  Much of the song's data is read from the songs_albums_by_song table. This includes
 *  the song name, genre, similar songs, albums and songs. Some of this data is denormalized
 *  so it is only retrieved from the first of potentially multiple rows.
 *  
 *  The song's community rating is retrieved from the average_ratings table. 
 *
 *  The song's user rating is retrieved from the user_data_by_user table. 
 *
 * @author billschwanitz
 */
public class SongInfoQuery {
	/*
	 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
	 * SongInfo response.
	 * 
	 * @param session		the Datastax session through which queries and mappings should be executed
	 * @param userId		the UUID of the logged in user making the query (will be null if user not logged in)
	 * @param songUuid	the UUID of the song to be retrieved
	 * 
	 * @return the SongInfo instance with all the info on the requested user
	 */
	public static SongBo get(final Session session, final UUID userUuid, final UUID songUuid) {
		SongBo songBo = new SongBo();
		
		/*
		 * Retrieve data from the songs_albums_by_song table
		 */
		
		// Build query to select all rows for the given song from the album_artist_by_song table 
		Statement statement1 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_ALBUM_ARTIST_BY_SONG)
				.where(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_SONG_ID, songUuid));
		
		// Execute the query and map result to DAO POJO
		AlbumArtistBySongDao albumArtistBySongDao = new MappingManager(session).mapper(AlbumArtistBySongDao.class).map(session.execute(statement1)).one();

		// If we didn't retrieved a DAO, then return null 
		if (null == albumArtistBySongDao) {
			return null;
		}
		
		songBo.setSongId(albumArtistBySongDao.getSongId());
		songBo.setSongName(albumArtistBySongDao.getSongName());
		songBo.setAlbumId(albumArtistBySongDao.getAlbumId());
		songBo.setAlbumName(albumArtistBySongDao.getAlbumName());
		songBo.setArtistId(albumArtistBySongDao.getArtistId());
		songBo.setArtistName(albumArtistBySongDao.getArtistName());
		songBo.setDuration(albumArtistBySongDao.getSongDuration());
		songBo.setYear(albumArtistBySongDao.getAlbumYear());
		
		if (albumArtistBySongDao.getArtistGenres() != null && albumArtistBySongDao.getArtistGenres().size()>0) {
			songBo.setGenre(albumArtistBySongDao.getArtistGenres().iterator().next());
		}
				
		/*
		 * Retrieve data from the average_ratings table
		 */
		
		// Build query to select all rows for the given song from the average_ratings table 
		Statement statement2 = QueryBuilder.select()
				.all()
				.from(CassandraConstants.MSL_TABLE_AVERAGE_RATINGS)
				.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, songUuid))
					.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.SONG.dbContentType));
		
		// Execute the query and map single result row to a DAO POJOs
		AverageRatingsDao averageRatingsDao = new MappingManager(session).mapper(AverageRatingsDao.class).map(session.execute(statement2)).one();
		
		// If we retrieved a DAO, then include that info into the SongInfo 
		if (null != averageRatingsDao) {
			songBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
		}
		
		/*
		 * Retrieve data from the user_data_by_user table if a user UUID was passed
		 */
		
		if (userUuid != null) {
			// Build query to select all rows for the given song from the user_data_by_user table 
			Statement statement3 = QueryBuilder.select()
					.all()
					.from(CassandraConstants.MSL_TABLE_USER_DATA_BY_USER)
					.where().and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_USER_ID, userUuid))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_TYPE, MSL_CONTENT_TYPE.SONG.dbContentType))
						.and(QueryBuilder.eq(CassandraConstants.MSL_COLUMN_CONTENT_ID, songUuid));
			
			// Execute the query and map single result row to a DAO POJOs
			UserDataByUserDao userDataByUserDao = new MappingManager(session).mapper(UserDataByUserDao.class).map(session.execute(statement3)).one();

			// If we retrieved a DAO, then include that info into the SongInfo 
			if (null != userDataByUserDao) {
				songBo.setPersonalRating(userDataByUserDao.getRating());
			}
		}
		
		return songBo;
	}
}
