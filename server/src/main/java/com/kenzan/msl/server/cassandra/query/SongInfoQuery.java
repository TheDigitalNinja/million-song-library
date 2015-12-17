/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.AlbumArtistBySongDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.MyLibrary;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an SongInfo
 * response.
 * 
 * Much of the song's data is read from the songs_albums_by_song table. This includes the song name,
 * genre, similar songs, albums and songs. Some of this data is denormalized so it is only retrieved
 * from the first of potentially multiple rows.
 * 
 * The song's community rating is retrieved from the average_ratings table.
 *
 * The song's user rating is retrieved from the user_data_by_user table.
 *
 * @author billschwanitz
 */
public class SongInfoQuery {
    /*
     * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
     * SongInfo response.
     * 
     * @param queryAccessor the Datastax QueryAccessor declaring out prepared queries
     * 
     * @param mappingManager the Datastax MappingManager responsible for executing queries and
     * mapping results to POJOs
     * 
     * @param userId the UUID of the logged in user making the query (will be null if user not
     * logged in)
     * 
     * @param songUuid the UUID of the song to be retrieved
     * 
     * @return Optional SongInfo instance with all the info on the requested user
     */
    public static Optional<SongBo> get(final QueryAccessor queryAccessor, final MappingManager mappingManager,
                                       final UUID userUuid, final UUID songUuid) {
        SongBo songBo = new SongBo();

        /*
         * Retrieve data from the album_artist_by_song table
         */

        // Select all rows for the given song from the album_artist_by_song table
        AlbumArtistBySongDao albumArtistBySongDao = mappingManager.mapper(AlbumArtistBySongDao.class)
            .map(queryAccessor.albumArtistBySong(songUuid)).one();

        // If we didn't retrieved a DAO, then return absent()
        if ( null == albumArtistBySongDao ) {
            return Optional.absent();
        }

        songBo.setSongId(albumArtistBySongDao.getSongId());
        songBo.setSongName(albumArtistBySongDao.getSongName());
        songBo.setAlbumId(albumArtistBySongDao.getAlbumId());
        songBo.setAlbumName(albumArtistBySongDao.getAlbumName());
        songBo.setArtistId(albumArtistBySongDao.getArtistId());
        songBo.setArtistName(albumArtistBySongDao.getArtistName());
        songBo.setDuration(albumArtistBySongDao.getSongDuration());
        songBo.setYear(albumArtistBySongDao.getAlbumYear());

        if ( albumArtistBySongDao.getArtistGenres() != null && albumArtistBySongDao.getArtistGenres().size() > 0 ) {
            songBo.setGenre(albumArtistBySongDao.getArtistGenres().iterator().next());
        }

        // Adds isInLibrary tag and timestamp to songBo result
        if ( null != userUuid ) {
            MyLibrary myLibrary = LibraryQuery.get(queryAccessor, mappingManager, userUuid.toString());
            if ( LibraryQuery.isInLibrary(songBo, myLibrary) ) {
                songBo.setInMyLibrary(true);
            }
        }

        /*
         * Retrieve average rating for the song
         */

        // Select the row for the song from the average_ratings table
        AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
            .map(queryAccessor.songAverageRating(songUuid).getUninterruptibly()).one();

        // If we retrieved a DAO, then include that info into the SongInfo
        if ( null != averageRatingsDao ) {
            songBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
        }

        /*
         * Retrieve user's rating for the song if a user UUID was passed
         */

        if ( userUuid != null ) {
            // Select the row for the artist and user from the user_data_by_user table
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(queryAccessor.songUserRating(userUuid, songUuid).getUninterruptibly()).one();

            // If we retrieved a DAO, then include that info into the SongInfo
            if ( null != userDataByUserDao ) {
                songBo.setPersonalRating(userDataByUserDao.getRating());
            }
        }

        return Optional.of(songBo);
    }
}
