/*
 * Copyright 2015, Kenzan, All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.base.Optional;
import com.kenzan.msl.server.bo.AlbumBo;
import com.kenzan.msl.server.bo.ArtistBo;
import com.kenzan.msl.server.bo.SongBo;
import com.kenzan.msl.server.cassandra.CassandraConstants;
import com.kenzan.msl.server.cassandra.QueryAccessor;
import com.kenzan.msl.server.cassandra.query.AlbumInfoQuery;
import com.kenzan.msl.server.cassandra.query.AlbumListQuery;
import com.kenzan.msl.server.cassandra.query.ArtistInfoQuery;
import com.kenzan.msl.server.cassandra.query.ArtistListQuery;
import com.kenzan.msl.server.cassandra.query.SongInfoQuery;
import com.kenzan.msl.server.cassandra.query.SongListQuery;
import com.kenzan.msl.server.translate.Translators;
import com.kenzan.msl.server.cassandra.query.AuthenticationQuery;
import com.kenzan.msl.server.cassandra.query.LibraryQuery;

import java.util.UUID;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;
import io.swagger.model.MyLibrary;

import org.apache.commons.lang3.StringUtils;

import rx.Observable;

/**
 * Implementation of the CatalogService interface that retrieves its data from a Cassandra cluster.
 *
 * @author billschwanitz
 */
public class CassandraCatalogService
    implements CatalogService {

    private QueryAccessor queryAccessor;
    private MappingManager mappingManager;

    public CassandraCatalogService() {
        // TODO: Get the contact point from config param
        Cluster cluster = Cluster.builder().addContactPoint("127.0.0.1").build();

        // TODO: Get the keyspace from config param
        Session session = cluster.connect(CassandraConstants.MSL_KEYSPACE);

        mappingManager = new MappingManager(session);
        queryAccessor = mappingManager.createAccessor(QueryAccessor.class);
    }

    // ==========================================================================================================
    // ALBUMS
    // =================================================================================================================

    /**
     * Get browsing data for albums in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The first page is retrieved
     * by passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved
     * by passing the <code>pagingState</code> that accompanied the previously retrieved page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when retrieving the first
     * page. This value is used for all subsequent pages, (the <code>items</code> parameter is
     * ignored when retrieving subsequent pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
     * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored
     * when retrieving subsequent pages).
     *
     * @param pagingState Used for pagination control. To retrieve the first page, use
     *            <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code>
     *            that accompanied the previous page.
     * @param items Specifies the number of items to include in each page. This value is only
     *            necessary on the retrieval of the first page, and will be used for all subsequent
     *            pages.
     * @param facets Specifies a comma delimited list of search facet Ids to filter the results.
     *            Pass null or an empty string to not filter.
     * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null
     *            for unauthenticated requests.
     * @return Observable<AlbumList>
     */
    public Observable<AlbumList> browseAlbums(String pagingState, Integer items, String facets, String userId) {
        UUID pagingStateUuid = StringUtils.isEmpty(pagingState) ? null : UUID.fromString(pagingState);
        UUID userUuid = StringUtils.isEmpty(userId) ? null : UUID.fromString(userId);

        return Observable
            .just(Translators.translate(new AlbumListQuery().get(queryAccessor, mappingManager, pagingStateUuid, items,
                                                                 facets, userUuid)));
    }

    /**
     * Get data on an album in the catalog.
     *
     * @param albumId Specifies the UUID of the album to retrieve.
     * @param userId Specifies the UUID of the authenticated user.
     * @return Observable<Optional<AlbumInfo>>
     */
    public Observable<Optional<AlbumInfo>> getAlbum(String albumId, String userId) {
        UUID albumUuid = UUID.fromString(albumId);
        UUID userUuid = null == userId ? null : UUID.fromString(userId);

        Optional<AlbumBo> optAlbumBo = AlbumInfoQuery.get(queryAccessor, mappingManager, userUuid, albumUuid);
        if ( !optAlbumBo.isPresent() ) {
            return Observable.just(Optional.absent());
        }
        return Observable.just(Optional.of(Translators.translate(optAlbumBo.get())));
    }

    // =========================================================================================================
    // ARTISTS
    // =================================================================================================================

    /**
     * Get browsing data for artists in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The first page is retrieved
     * by passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved
     * by passing the <code>pagingState</code> that accompanied the previously retrieved page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when retrieving the first
     * page. This value is used for all subsequent pages, (the <code>items</code> parameter is
     * ignored when retrieving subsequent pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
     * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored
     * when retrieving subsequent pages).
     *
     * @param pagingState Used for pagination control. To retrieve the first page, use
     *            <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code>
     *            that accompanied the previous page.
     * @param items Specifies the number of items to include in each page. This value is only
     *            necessary on the retrieval of the first page, and will be used for all subsequent
     *            pages.
     * @param facets Specifies a comma delimited list of search facet Ids to filter the results.
     *            Pass null or an empty string to not filter.
     * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null
     *            for unauthenticated requests.
     * @return Observable<ArtistList>
     */
    public Observable<ArtistList> browseArtists(String pagingState, Integer items, String facets, String userId) {
        UUID pagingStateUuid = StringUtils.isEmpty(pagingState) ? null : UUID.fromString(pagingState);
        UUID userUuid = StringUtils.isEmpty(userId) ? null : UUID.fromString(userId);

        return Observable.just(Translators.translate(new ArtistListQuery()
            .get(queryAccessor, mappingManager, pagingStateUuid, items, facets, userUuid)));
    }

    /**
     * Get data on an artist in the catalog.
     *
     * @param artistId Specifies the UUID of the artist to retrieve.
     * @param userId Specifies the UUID of the authenticated user
     * @return Observable<Optional<ArtistInfo>>
     */
    public Observable<Optional<ArtistInfo>> getArtist(String artistId, String userId) {
        UUID artistUuid = UUID.fromString(artistId);
        UUID userUuid = (null == userId) ? null : UUID.fromString(userId);

        Optional<ArtistBo> optArtistBo = ArtistInfoQuery.get(queryAccessor, mappingManager, userUuid, artistUuid);
        if ( !optArtistBo.isPresent() ) {
            return Observable.just(Optional.absent());
        }
        return Observable.just(Optional.of(Translators.translate(optArtistBo.get())));
    }

    // ===========================================================================================================
    // SONGS
    // =================================================================================================================

    /**
     * Get browsing data for songs in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The first page is retrieved
     * by passing <code>null</code> as the <code>pagingState</code>. Subsequent pages are retrieved
     * by passing the <code>pagingState</code> that accompanied the previously retrieved page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when retrieving the first
     * page. This value is used for all subsequent pages, (the <code>items</code> parameter is
     * ignored when retrieving subsequent pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when retrieving the first page.
     * This value is used for all subsequent pages, (the <code>facets</code> parameter is ignored
     * when retrieving subsequent pages).
     *
     * @param pagingState Used for pagination control. To retrieve the first page, use
     *            <code>null</code>. To retrieve subsequent pages, use the <code>pagingState</code>
     *            that accompanied the previous page.
     * @param items Specifies the number of items to include in each page. This value is only
     *            necessary on the retrieval of the first page, and will be used for all subsequent
     *            pages.
     * @param facets Specifies a comma delimited list of search facet Ids to filter the results.
     *            Pass null or an empty string to not filter.
     * @param userId Specifies a user UUID identifying the currently logged-in user. Will be null
     *            for unauthenticated requests.
     * @return Observable<SongList>
     */
    public Observable<SongList> browseSongs(String pagingState, Integer items, String facets, String userId) {
        UUID pagingStateUuid = StringUtils.isEmpty(pagingState) ? null : UUID.fromString(pagingState);
        UUID userUuid = StringUtils.isEmpty(userId) ? null : UUID.fromString(userId);

        return Observable
            .just(Translators.translate(new SongListQuery().get(queryAccessor, mappingManager, pagingStateUuid, items,
                                                                facets, userUuid)));
    }

    /**
     * Get data on a song in the catalog.
     *
     * @param songId Specifies the UUID of the song to retrieve.
     * @param userId Specifies the UUID of the authenticated user.
     * @return Observable<Optional<SongInfo>>
     */
    public Observable<Optional<SongInfo>> getSong(String songId, String userId) {
        UUID songUuid = UUID.fromString(songId);
        UUID userUuid = null == userId ? null : UUID.fromString(userId);

        Optional<SongBo> optSongBo = SongInfoQuery.get(queryAccessor, mappingManager, userUuid, songUuid);
        if ( !optSongBo.isPresent() ) {
            return Observable.just(Optional.absent());
        }
        return Observable.just(Optional.of(Translators.translate(optSongBo.get())));
    }

    /**
     * Gets UUID to use as sessionToken on valid credentials
     *
     * @param email user email
     * @param password user password
     * @return Observable<UUID>
     */
    // TODO move this to a dedicated CassandraAuthenticationService
    public Observable<Optional<UUID>> logIn(String email, String password) {
        return Observable.just(AuthenticationQuery.authenticate(queryAccessor, email, password));
    }

    /**
     * Retrieves the user library data
     *
     * @param sessionToken user uuid
     * @return Observable<MyLibrary>
     */
    public Observable<MyLibrary> getMyLibrary(String sessionToken) {
        return Observable.just(LibraryQuery.get(queryAccessor, mappingManager, sessionToken));
    }
}
