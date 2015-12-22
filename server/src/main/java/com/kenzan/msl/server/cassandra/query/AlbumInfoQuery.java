/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.SongsArtistByAlbumDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.MyLibrary;

import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * AlbumInfo response.
 * 
 * Much of the album's data is read from the songs_artist_by_album table. This includes the album
 * name, genre, similar albums, albums and songs. Some of this data is denormalized so it is only
 * retrieved from the first of potentially multiple rows.
 * 
 * The album's community rating is retrieved from the average_ratings table.
 *
 * The album's user rating is retrieved from the user_data_by_user table.
 *
 * @author billschwanitz
 */
public class AlbumInfoQuery {
    /**
     * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
     * AlbumInfo response.
     * 
     * @param queryAccessor the Datastax QueryAccessor declaring out prepared queries
     * @param mappingManager the Datastax MappingManager responsible for executing queries and
     *            mapping results to POJOs
     * @param userUuid the UUID of the logged in user making the query (will be null if user not
     *            logged in)
     * @param albumUuid the UUID of the album to be retrieved
     * 
     * @return Optional AlbumInfo instance with all the info on the requested user
     */
    public static Optional<AlbumBo> get(final QueryAccessor queryAccessor, final MappingManager mappingManager,
                                        final UUID userUuid, final UUID albumUuid) {
        AlbumBo albumBo = new AlbumBo();

        // Retrieve data from the songs_artist_by_album table
        // Select all rows for the given album from the songs_artist_by_album table
        Result<SongsArtistByAlbumDao> results1 = mappingManager.mapper(SongsArtistByAlbumDao.class)
            .map(queryAccessor.songsArtistByAlbum(albumUuid));

        // Loop through the results rows building up the AlbumInfo
        boolean processedOneRow = false;
        for ( SongsArtistByAlbumDao songsArtistByAlbumDao : results1 ) {
            if ( false == processedOneRow ) {
                albumBo.setAlbumId(songsArtistByAlbumDao.getAlbumId());
                albumBo.setAlbumName(songsArtistByAlbumDao.getAlbumName());
                albumBo.setArtistId(songsArtistByAlbumDao.getArtistId());
                albumBo.setArtistName(songsArtistByAlbumDao.getArtistName());
				albumBo.setImageLink(songsArtistByAlbumDao.getImageLink());

                if ( songsArtistByAlbumDao.getArtistGenres() != null
                    && songsArtistByAlbumDao.getArtistGenres().size() > 0 ) {
                    albumBo.setGenre(songsArtistByAlbumDao.getArtistGenres().iterator().next());
                }

                processedOneRow = true;
            }

            // Add the song ID from this DAO if it is not already in the list
            if ( !albumBo.getSongsList().contains(songsArtistByAlbumDao.getSongId().toString()) ) {
                albumBo.getSongsList().add(songsArtistByAlbumDao.getSongId().toString());
            }
        }

        // If we didn't retrieved a DAO, then return absent()
        if ( !processedOneRow ) {
            return Optional.absent();
        }

        // Adds isInLibrary tag and timestamp to albumBo result
        if ( null != userUuid ) {
            MyLibrary myLibrary = LibraryQuery.get(queryAccessor, mappingManager, userUuid.toString());
            if ( LibraryQuery.isInLibrary(albumBo, myLibrary) ) {
                albumBo.setInMyLibrary(true);
            }
        }

        // Retrieve average rating for the album
        // Select the row for the album from the average_ratings table
        AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
            .map(queryAccessor.albumAverageRating(albumUuid).getUninterruptibly()).one();

        // If we retrieved a DAO, then include that info into the AlbumInfo
        if ( null != averageRatingsDao ) {
            albumBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao.getNumRating()));
        }

        // Retrieve user's rating for the album if a user UUID was passed
        if ( userUuid != null ) {
            // Select the row for the album and user from the user_data_by_user table
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(queryAccessor.albumUserRating(userUuid, albumUuid).getUninterruptibly()).one();

            // If we retrieved a DAO, then include that info into the AlbumInfo
            if ( null != userDataByUserDao ) {
                albumBo.setPersonalRating(userDataByUserDao.getRating());
            }
        }

        return Optional.of(albumBo);
    }
}
