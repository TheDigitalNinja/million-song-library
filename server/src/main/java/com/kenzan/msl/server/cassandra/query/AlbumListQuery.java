/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.cassandra.query;

import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.MappingManager;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.AlbumListBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.dao.AverageRatingsDao;
import com.kenzan.msl.server.dao.UserDataByUserDao;
import io.swagger.model.MyLibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
 * AlbumList response. This is a paginated query, so the initial query will select a page of albums
 * from a single table (based on the facets, if any, that were passed from the caller).
 * 
 * The much of the album's data is read from the songs_artist_by_album table. This includes the
 * album name, album year, genre, artists and songs. Some of this data is denormalized so it is only
 * retrieved from the first of potentially multiple rows.
 * 
 * The album's community rating is retrieved from the average_ratings table.
 *
 * The album's user rating is retrieved from the user_data_by_user table.
 *
 * @author billschwanitz
 */
public class AlbumListQuery
    implements PaginatorHelper {
    /*
     * Performs queries to numerous Cassandra tables to assemble all the pieces that make up an
     * AlbumList response.
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
     * @return the AlbumList instance with all the info for the requested page
     */
    public AlbumListBo get(final QueryAccessor queryAccessor, final MappingManager mappingManager,
                           final UUID pagingStateUuid, final Integer items, final String facets, final UUID userUuid) {
        AlbumListBo albumListBo = new AlbumListBo();

        // Retrieve the requested page of Albums
        new Paginator(CassandraConstants.MSL_CONTENT_TYPE.ALBUM, queryAccessor, mappingManager, this, pagingStateUuid,
                      items, facets).getPage(albumListBo);

        // Adds isInLibrary tag to albumList results
        if ( null != userUuid ) {
            MyLibrary myLibrary = LibraryQuery.get(queryAccessor, mappingManager, userUuid.toString());
            for ( AlbumBo albumBo : albumListBo.getBoList() ) {
                if ( LibraryQuery.isInLibrary(albumBo, myLibrary) ) {
                    albumBo.setInMyLibrary(true);
                }
            }
        }
        /*
         * Asynchronously query for the average and user ratings for each album.
         * 
         * NOTE: this could be done using CQL's IN keyword, but that leads to scalability issues.
         * See the great discussion of this issue here:
         * https://lostechies.com/ryansvihla/2014/09/22/
         * cassandra-query-patterns-not-using-the-in-query-for-multiple-partitions
         */
        Set<ResultSetFuture> averageRatingFutures = fireAverageRatingQueries(queryAccessor, albumListBo);
        Set<ResultSetFuture> userRatingFutures = null;
        if ( null != userUuid ) {
            userRatingFutures = fireUserRatingQueries(queryAccessor, albumListBo, userUuid);
        }

        // Now wait for the asynchronous queries to complete and roll the data into the list
        processAverageRatingResults(mappingManager, albumListBo, averageRatingFutures);
        if ( null != userUuid ) {
            processUserRatingResults(mappingManager, albumListBo, userRatingFutures);
        }

        return albumListBo;
    }

    /*
     * Query for average rating for each album DAO in the list. Instead of using a single query with
     * an IN clause on a partition key column, which can have scalability issues, we will fire of
     * one asynchronous query for each album in the list.
     */
    private Set<ResultSetFuture> fireAverageRatingQueries(final QueryAccessor queryAccessor,
                                                          final AlbumListBo albumListBo) {
        Set<ResultSetFuture> returnSet = new HashSet<>(albumListBo.getBoList().size());

        for ( AlbumBo albumBo : albumListBo.getBoList() ) {
            returnSet.add(queryAccessor.albumAverageRating(albumBo.getAlbumId()));
        }

        return returnSet;
    }

    /*
     * Query for user's rating for each album DAO in the list.
     * 
     * Instead of using a single query with an IN clause on a partition key column, which can have
     * scalability issues, we will fire of one asynchronous query for each album in the list.
     */
    private Set<ResultSetFuture> fireUserRatingQueries(final QueryAccessor queryAccessor,
                                                       final AlbumListBo albumListBo, final UUID userUuid) {
        Set<ResultSetFuture> returnSet = new HashSet<>(albumListBo.getBoList().size());

        for ( AlbumBo albumBo : albumListBo.getBoList() ) {
            returnSet.add(queryAccessor.albumUserRating(userUuid, albumBo.getAlbumId()));
        }

        return returnSet;
    }

    /*
     * Process the results from the queries for average rating for each album DAO in the list.
     */
    private void processAverageRatingResults(final MappingManager mappingManager, final AlbumListBo albumListBo,
                                             final Set<ResultSetFuture> resultFutures) {
        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            AverageRatingsDao averageRatingsDao = mappingManager.mapper(AverageRatingsDao.class)
                .map(future.getUninterruptibly()).one();

            if ( null != averageRatingsDao ) {
                // Find and update the matching AlbumBo
                for ( AlbumBo albumBo : albumListBo.getBoList() ) {
                    if ( albumBo.getAlbumId().equals(averageRatingsDao.getContentId()) ) {
                        albumBo.setAverageRating((int) (averageRatingsDao.getSumRating() / averageRatingsDao
                            .getNumRating()));
                        break;
                    }
                }
            }
        }
    }

    /*
     * Process the results from the queries for user rating for each artist DAO in the list.
     */
    private void processUserRatingResults(final MappingManager mappingManager, final AlbumListBo artistListBo,
                                          final Set<ResultSetFuture> resultFutures) {
        for ( ResultSetFuture future : resultFutures ) {
            // Wait for the query to complete and map single result row to a DAO POJOs
            UserDataByUserDao userDataByUserDao = mappingManager.mapper(UserDataByUserDao.class)
                .map(future.getUninterruptibly()).one();

            if ( userDataByUserDao != null ) {
                // Find and update the matching AlbumBo
                for ( AlbumBo albumBo : artistListBo.getBoList() ) {
                    if ( albumBo.getAlbumId().equals(userDataByUserDao.getContentUuid()) ) {
                        albumBo.setPersonalRating(userDataByUserDao.getRating());
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
        return queryAccessor.albumsByFacet(facetName);
    }

    public Statement prepareFeaturedQuery(final QueryAccessor queryAccessor) {
        return queryAccessor.featuredAlbums();
    }

    public String getFacetedQueryString(final String facetName) {
        return QueryAccessor.FACETED_ALBUMS_QUERY.replace(":facet_name", "'" + facetName + "'");
    }

    public String getFeaturedQueryString() {
        return QueryAccessor.FEATURED_ALBUMS_QUERY;
    }
}
