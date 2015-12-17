/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.SongsAlbumsByArtistDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.MyLibrary;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * ArtistInfo response.
 * 
 * Much of the artist's data is read from the songs_albums_by_artist table. This includes the artist
 * name, genre, similar artists, albums and songs. Some of this data is denormalized so it is only
 * retrieved from the first of potentially multiple rows.
 * 
 * The artist's community rating is retrieved from the average_ratings table.
 *
 * The artist's user rating is retrieved from the user_data_by_user table.
 *
 * @author billschwanitz
 */
public class ArtistInfoQuery {
    /*
     * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
     * ArtistInfo response.
     * 
     * @param queryAccessor the Datastax QueryAccessor declaring out prepared queries
     * 
     * @param mappingManager the Datastax MappingManager responsible for executing queries and
     * mapping results to POJOs
     * 
     * @param userId the UUID of the logged in user making the query (will be null if user not
     * logged in)
     * 
     * @param artistUuid the UUID of the artist to be retrieved
     * 
     * @return Optional ArtistInfo instance with all the info on the requested user
     */
    public static Optional<ArtistBo> get(final QueryAccessor queryAccessor, final MappingManager mappingManager,
                                         final UUID userUuid, final UUID artistUuid) {
        ArtistBo artistBo = new ArtistBo();

        /*
         * Retrieve data from the songs_albums_by_artist table
         */

        // Select all rows for the given artist from the songs_albums_by_artist table
        Result<SongsAlbumsByArtistDao> results1 = mappingManager.mapper(SongsAlbumsByArtistDao.class)
            .map(queryAccessor.songsAlbumsByArtist(artistUuid));

        // Loop through the results rows building up the ArtistInfo
        boolean processedOneRow = false;
        for ( SongsAlbumsByArtistDao songsAlbumsByArtistDao : results1 ) {
            if ( false == processedOneRow ) {
                artistBo.setArtistId(songsAlbumsByArtistDao.getArtistId());
                artistBo.setArtistName(songsAlbumsByArtistDao.getArtistName());

                if ( songsAlbumsByArtistDao.getArtistGenres() != null
                    && songsAlbumsByArtistDao.getArtistGenres().size() > 0 ) {
                    artistBo.setGenre(songsAlbumsByArtistDao.getArtistGenres().iterator().next());
                }

                if ( songsAlbumsByArtistDao.getSimilarArtists() != null ) {
                    for ( UUID similarArtistUuid : songsAlbumsByArtistDao.getSimilarArtists().keySet() ) {
                        artistBo.getSimilarArtistsList().add(similarArtistUuid.toString());
                    }
                }

                processedOneRow = true;
            }

            // Add the album ID from this DAO if it is not already in the list
            if ( !artistBo.getAlbumsList().contains(songsAlbumsByArtistDao.getAlbumId().toString()) ) {
                artistBo.getAlbumsList().add(songsAlbumsByArtistDao.getAlbumId().toString());
            }

            // Add the song ID from this DAO if it is not already in the list
            if ( !artistBo.getSongsList().contains(songsAlbumsByArtistDao.getSongId().toString()) ) {
                artistBo.getSongsList().add(songsAlbumsByArtistDao.getSongId().toString());
            }
        }

        // If we didn't retrieved a DAO, then return null
        if ( !processedOneRow ) {
            return Optional.absent();
        }

        // Adds isInLibrary tag and timestamp to artistBo result
        if ( null != userUuid ) {
            MyLibrary myLibrary = LibraryQuery.get(queryAccessor, mappingManager, userUuid.toString());
            if ( LibraryQuery.isInLibrary(artistBo, myLibrary) ) {
                artistBo.setInMyLibrary(true);
            }
        }

        /*
         * Retrieve average rating for the artist
         */

        // Select the row for the artist from the average_ratings table
        AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
            .map(queryAccessor.artistAverageRating(artistUuid).getUninterruptibly()).one();

        // If we retrieved a DAO, then include that info into the ArtistInfo
        if ( null != averageRatingsDao ) {
            artistBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
        }

        /*
         * Retrieve user's rating for the artist if a user UUID was passed
         */

        if ( userUuid != null ) {
            // Select the row for the artist and user from the user_data_by_user table
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(queryAccessor.artistUserRating(userUuid, artistUuid).getUninterruptibly()).one();

            // If we retrieved a DAO, then include that info into the ArtistInfo
            if ( null != userDataByUserDao ) {
                artistBo.setPersonalRating(userDataByUserDao.getRating());
            }
        }

        return Optional.of(artistBo);
    }
}
