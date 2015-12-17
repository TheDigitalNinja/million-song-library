package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.AlbumInfo;
import io.swagger.model.ArtistInfo;
import io.swagger.model.SongInfo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class RatingHelper {

    /**
     * Processes user rating information for the supplied albumInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param albumInfoList List<AlbumInfo> object
     */
    public static void processAverageAlbumRatings(final QueryAccessor queryAccessor,
                                                  final MappingManager mappingManager,
                                                  final List<AlbumInfo> albumInfoList) {
        Set<ResultSetFuture> resultFutures = new HashSet<>(albumInfoList.size());

        for ( AlbumInfo albumInfo : albumInfoList ) {
            resultFutures.add(queryAccessor.albumAverageRating(UUID.fromString(albumInfo.getAlbumId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
                .map(future.getUninterruptibly()).one();

            if ( null != averageRatingsDao ) {
                // Find and update the matching Album
                for ( AlbumInfo albumInfo : albumInfoList ) {
                    if ( albumInfo.getAlbumId().equals(averageRatingsDao.getContentId().toString()) ) {
                        albumInfo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao
                            .getNumRating()));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Processes user rating information for the supplied songInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param songInfoList List<SongInfo> object
     */
    public static void processAverageSongRatings(final QueryAccessor queryAccessor,
                                                 final MappingManager mappingManager, final List<SongInfo> songInfoList) {
        Set<ResultSetFuture> resultFutures = new HashSet<>(songInfoList.size());

        for ( SongInfo songInfo : songInfoList ) {
            resultFutures.add(queryAccessor.songAverageRating(UUID.fromString(songInfo.getSongId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
                .map(future.getUninterruptibly()).one();

            if ( null != averageRatingsDao ) {
                // Find and update the matching Song
                for ( SongInfo songInfo : songInfoList ) {
                    if ( songInfo.getSongId().equals(averageRatingsDao.getContentId().toString()) ) {
                        songInfo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao
                            .getNumRating()));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Processes user rating information for the supplied artistInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param artistInfoList List<ArtistInfo> object
     */
    public static void processAverageArtistRatings(final QueryAccessor queryAccessor,
                                                   final MappingManager mappingManager,
                                                   final List<ArtistInfo> artistInfoList) {
        Set<ResultSetFuture> resultFutures = new HashSet<>(artistInfoList.size());

        for ( ArtistInfo artistInfo : artistInfoList ) {
            resultFutures.add(queryAccessor.artistAverageRating(UUID.fromString(artistInfo.getArtistId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
                .map(future.getUninterruptibly()).one();

            if ( null != averageRatingsDao ) {
                // Find and update the matching Artist
                for ( ArtistInfo artistInfo : artistInfoList ) {
                    if ( artistInfo.getArtistId().equals(averageRatingsDao.getContentId().toString()) ) {
                        artistInfo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao
                            .getNumRating()));
                        break;
                    }
                }
            }
        }
    }

    /**
     * Processes user rating information for the supplied albumInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param albumInfoList List<AlbumInfo> object
     * @param userUuid authenticated user uuid
     */
    public static void processUserAlbumRatingResults(final QueryAccessor queryAccessor,
                                                     final MappingManager mappingManager,
                                                     final List<AlbumInfo> albumInfoList, final UUID userUuid) {

        Set<ResultSetFuture> resultFutures = new HashSet<>(albumInfoList.size());

        for ( AlbumInfo albumInfo : albumInfoList ) {
            resultFutures.add(queryAccessor.artistUserRating(userUuid, UUID.fromString(albumInfo.getAlbumId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(future.getUninterruptibly()).one();

            // Find and update the matching Album
            if ( userDataByUserDao != null ) {
                for ( AlbumInfo albumInfo : albumInfoList ) {
                    if ( albumInfo.getAlbumId().equals(userDataByUserDao.getContentUuid().toString()) ) {
                        albumInfo.setPersonalRating(userDataByUserDao.getRating());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Processes user rating information for the supplied artistInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param artistInfoList List<ArtistInfo> object
     * @param userUuid authenticated user uuid
     */
    public static void processUserArtistRatingResults(final QueryAccessor queryAccessor,
                                                      final MappingManager mappingManager,
                                                      final List<ArtistInfo> artistInfoList, final UUID userUuid) {

        Set<ResultSetFuture> resultFutures = new HashSet<>(artistInfoList.size());

        for ( ArtistInfo artistInfo : artistInfoList ) {
            resultFutures.add(queryAccessor.artistUserRating(userUuid, UUID.fromString(artistInfo.getArtistId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(future.getUninterruptibly()).one();

            // Find and update the matching Artist
            if ( userDataByUserDao != null ) {
                for ( ArtistInfo artistInfo : artistInfoList ) {
                    if ( artistInfo.getArtistId().equals(userDataByUserDao.getContentUuid().toString()) ) {
                        artistInfo.setPersonalRating(userDataByUserDao.getRating());
                        break;
                    }
                }
            }
        }
    }

    /**
     * Processes user rating information for the supplied songInfoList
     *
     * @param queryAccessor com.datastax.driver.mapping.MappingManager query accessor
     * @param mappingManager com.datastax.driver.mapping.MappingManager
     * @param songInfoList List<SongInfo> object
     * @param userUuid authenticated user uuid
     */
    public static void processUserSongRatingResults(final QueryAccessor queryAccessor,
                                                    final MappingManager mappingManager,
                                                    final List<SongInfo> songInfoList, final UUID userUuid) {

        Set<ResultSetFuture> resultFutures = new HashSet<>(songInfoList.size());

        for ( SongInfo songInfo : songInfoList ) {
            resultFutures.add(queryAccessor.artistUserRating(userUuid, UUID.fromString(songInfo.getSongId())));
        }

        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(future.getUninterruptibly()).one();

            // Find and update the matching Song
            if ( userDataByUserDao != null ) {
                for ( SongInfo songInfo : songInfoList ) {
                    if ( userDataByUserDao.getContentUuid() != null ) {
                        if ( songInfo.getSongId().equals(userDataByUserDao.getContentUuid().toString()) ) {
                            songInfo.setPersonalRating(userDataByUserDao.getRating());
                            break;
                        }
                    }
                }
            }
        }
    }

}
