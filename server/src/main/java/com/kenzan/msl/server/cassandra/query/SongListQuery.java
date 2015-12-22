/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.bo.SongListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.MyLibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up a SongList
 * response. This is a paginated query, so the initial query will select a page of songs from a
 * single table (based on the facets, if any, that were passed from the caller).
 * 
 * The much of the song's data is read from the album_artist_by_song table. This includes the song
 * name, genre, similar artists, album and artist.
 * 
 * The song's community rating is retrieved from the average_ratings table.
 *
 * The song's user rating is retrieved from the user_data_by_user table.
 *
 * @author billschwanitz
 */
public class SongListQuery
    implements PaginatorHelper {
    /*
     * Performs queries to numerous Cassandra tables to assemble all the pieces that make up a
     * SongList response.
     * 
     * @param queryAccessor the Datastax QueryAccessor declaring out prepared queries
     * 
     * @param mappingManager the Datastax MappingManager responsible for executing queries and
     * mapping results to POJOs
     * 
     * @param pagingStateUuid Used for pagination control. To retrieve the first page, use
     * <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code> that
     * accompanied the previous page.
     * 
     * @param items Specifies the number of items to include in each page. This value is only
     * necessary on the retrieval of the first page, and will be used for all subsequent pages.
     * 
     * @param facets Specifies a comma delimited list of search facet Ids to filter the results.
     * Pass null or an empty string to not filter.
     * 
     * @param userUuid Specifies a user UUID identifying the currently logged-in user. Will be null
     * for unauthenticated requests.
     * 
     * @return the SongList instance with all the info for the requested page
     */
    public SongListBo get(final QueryAccessor queryAccessor, final MappingManager mappingManager,
                          final UUID pagingStateUuid, final Integer items, final String facets, final UUID userUuid) {
        SongListBo songListBo = new SongListBo();

        // Retrieve the requested page of Songs
        new Paginator(CassandraConstants.MSL_CONTENT_TYPE.SONG, queryAccessor, mappingManager, this, pagingStateUuid,
                      items, facets).getPage(songListBo);

        // Adds isInLibrary tag to songList results
        if ( null != userUuid ) {
            MyLibrary myLibrary = LibraryQuery.get(queryAccessor, mappingManager, userUuid.toString());
            for ( SongBo songBo : songListBo.getBoList() ) {
                if ( LibraryQuery.isInLibrary(songBo, myLibrary) ) {
                    songBo.setInMyLibrary(true);
                }
            }
        }
        /*
         * Asynchronously query for the average and user ratings for each song.
         * 
         * NOTE: this could be done using CQL's IN keyword, but that leads to scalability issues.
         * See the great discussion of this issue here:
         * https://lostechies.com/ryansvihla/2014/09/22/
         * cassandra-query-patterns-not-using-the-in-query-for-multiple-partitions
         */
        Set<ResultSetFuture> averageRatingFutures = fireAverageRatingQueries(queryAccessor, songListBo);
        Set<ResultSetFuture> userRatingFutures = null;
        if ( null != userUuid ) {
            userRatingFutures = fireUserRatingQueries(queryAccessor, songListBo, userUuid);
        }

        // Now wait for the asynchronous queries to complete and roll the data into the list
        processAverageRatingResults(mappingManager, songListBo, averageRatingFutures);
        if ( null != userUuid ) {
            processUserRatingResults(mappingManager, songListBo, userRatingFutures);
        }

        return songListBo;
    }

    /*
     * Query for average rating for each song DAO in the list. Instead of using a single query with
     * an IN clause on a partition key column, which can have scalability issues, we will fire of
     * one asynchronous query for each song in the list.
     */
    private Set<ResultSetFuture> fireAverageRatingQueries(final QueryAccessor queryAccessor, final SongListBo songListBo) {
        Set<ResultSetFuture> returnSet = new HashSet<>(songListBo.getBoList().size());

        for ( SongBo songBo : songListBo.getBoList() ) {
            returnSet.add(queryAccessor.songAverageRating(songBo.getSongId()));
        }

        return returnSet;
    }

    /*
     * Query for user's rating for each song DAO in the list.
     * 
     * Instead of using a single query with an IN clause on a partition key column, which can have
     * scalability issues, we will fire of one asynchronous query for each song in the list.
     */
    private Set<ResultSetFuture> fireUserRatingQueries(final QueryAccessor queryAccessor, final SongListBo songListBo,
                                                       final UUID userUuid) {
        Set<ResultSetFuture> returnSet = new HashSet<>(songListBo.getBoList().size());

        for ( SongBo songBo : songListBo.getBoList() ) {
            returnSet.add(queryAccessor.songUserRating(userUuid, songBo.getSongId()));
        }

        return returnSet;
    }

    /*
     * Process the results from the queries for average rating for each song DAO in the list.
     */
    private void processAverageRatingResults(final MappingManager mappingManager, final SongListBo songListBo,
                                             final Set<ResultSetFuture> resultFutures) {
        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
                .map(future.getUninterruptibly()).one();

            if ( null != averageRatingsDao ) {
                // Find and update the matching SongBo
                for ( SongBo songBo : songListBo.getBoList() ) {
                    if ( songBo.getSongId().equals(averageRatingsDao.getContentId()) ) {
                        songBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao
                            .getNumRating()));
                        break;
                    }
                }
            }
        }
    }

    /*
     * Process the results from the queries for user rating for each song DAO in the list.
     */
    private void processUserRatingResults(final MappingManager mappingManager, final SongListBo songListBo,
                                          final Set<ResultSetFuture> resultFutures) {
        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(future.getUninterruptibly()).one();

            if ( userDataByUserDao != null ) {
                // Find and update the matching SongBo
                for ( SongBo songBo : songListBo.getBoList() ) {
                    if ( songBo.getSongId().equals(userDataByUserDao.getContentUuid()) ) {
                        songBo.setPersonalRating(userDataByUserDao.getRating());
                        break;
                    }
                }
            }
        }
    }

    /*
     * Methods to support being a PaginatorHelper
     */

    public Statement prepareFacetedQuery(final QueryAccessor queryAccessor, final String facetName) {
        return queryAccessor.songsByFacet(facetName);
    }

    public Statement prepareFeaturedQuery(final QueryAccessor queryAccessor) {
        return queryAccessor.featuredSongs();
    }

    public String getFacetedQueryString(final String facetName) {
        return QueryAccessor.FACETED_SONGS_QUERY.replace(":facet_name", "'" + facetName + "'");
    }

    public String getFeaturedQueryString() {
        return QueryAccessor.FEATURED_SONGS_QUERY;
    }
}
