/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.services;

import com.google.common.base.Optional;
import com.kenzan.msl.server.mock.AlbumMockData;
import com.kenzan.msl.server.mock.ArtistMockData;
import com.kenzan.msl.server.mock.SongMockData;
import com.kenzan.msl.server.mock.LogInMockData;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;
import rx.Observable;

import java.util.UUID;

/**
 * Implementation of the CatalogService interface that mocks the data it returns.
 *
 * @author billschwanitz
 */
public class StubCatalogService implements CatalogService {

    private AlbumMockData albumMockData = new AlbumMockData();
    private ArtistMockData artistMockData = new ArtistMockData();
    private SongMockData songMockData = new SongMockData();
    private LogInMockData logInMockData = new LogInMockData();


    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    /**
     * Get browsing data for albums in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The
     * first page is retrieved by passing <code>null</code> as the
     * <code>pagingState</code>. Subsequent pages are retrieved by passing
     * the <code>pagingState</code> that accompanied the previously retrieved
     * page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>items</code> parameter is ignored when retrieving subsequent
     * pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>facets</code> parameter is ignored when retrieving subsequent
     * pages).
     *
     * @param pagingState Used for pagination control.
     *                    To retrieve the first page, use <code>null</code>.
     *                    To retrieve subsequent pages, use the
     *                    <code>pagingState</code> that accompanied the
     *                    previous page.
     * @param items       Specifies the number of items to include in each page.
     *                    This value is only necessary on the retrieval of the
     *                    first page, and will be used for all subsequent
     *                    pages.
     * @param facets      Specifies a comma delimited list of search facet Ids
     *                    to filter the results.
     *                    Pass null or an empty string to not filter.
     * @param userId      Specifies a user UUID identifying the currently logged-in
     *                    user. Will be null for unauthenticated requests.
     * @return Observable<AlbumList>
     */
    public Observable<AlbumList> browseAlbums(String pagingState, Integer items, String facets, String userId) {
        return Observable.just(albumMockData.browseAlbums(pagingState, items, facets));
    }

    /**
     * Get data on an album in the catalog.
     *
     * @param albumId Specifies the UUID of the album to retrieve.
     * @return Observable<AlbumInfo>
     */
    public Observable<Optional<AlbumInfo>> getAlbum(String albumId, String userId) {
        return Observable.just(Optional.of(albumMockData.getAlbum(albumId)));
    }


    // ========================================================================================================= ARTISTS
    // =================================================================================================================

    /**
     * Get browsing data for artists in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The
     * first page is retrieved by passing <code>null</code> as the
     * <code>pagingState</code>. Subsequent pages are retrieved by passing
     * the <code>pagingState</code> that accompanied the previously retrieved
     * page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>items</code> parameter is ignored when retrieving subsequent
     * pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>facets</code> parameter is ignored when retrieving subsequent
     * pages).
     *
     * @param pagingState Used for pagination control.
     *                    To retrieve the first page, use <code>null</code>.
     *                    To retrieve subsequent pages, use the
     *                    <code>pagingState</code> that accompanied the
     *                    previous page.
     * @param items       Specifies the number of items to include in each page.
     *                    This value is only necessary on the retrieval of the
     *                    first page, and will be used for all subsequent
     *                    pages.
     * @param facets      Specifies a comma delimited list of search facet Ids
     *                    to filter the results.
     *                    Pass null or an empty string to not filter.
     * @param userId      Specifies a user UUID identifying the currently logged-in
     *                    user. Will be null for unauthenticated requests.
     * @return Observable<ArtistList>
     */
    public Observable<ArtistList> browseArtists(String pagingState, Integer items, String facets, String userId) {
        return Observable.just(artistMockData.browseArtists(pagingState, items, facets));
    }

    /**
     * Get data on an artist in the catalog.
     *
     * @param artistId Specifies the UUID of the artist to retrieve.
     * @param userId   Specifies the UUID of the authenticated user
     * @return Observable<ArtistInfo>
     */
    public Observable<Optional<ArtistInfo>> getArtist(String artistId, String userId) {
        return Observable.just(Optional.of(artistMockData.getArtist(artistId)));
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    /**
     * Get browsing data for songs in the catalog.
     * <p/>
     * This is a paginated query - it returns data one page at a time. The
     * first page is retrieved by passing <code>null</code> as the
     * <code>pagingState</code>. Subsequent pages are retrieved by passing
     * the <code>pagingState</code> that accompanied the previously retrieved
     * page.
     * <p/>
     * The page size is determined by the <code>items</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>items</code> parameter is ignored when retrieving subsequent
     * pages).
     * <p/>
     * Data can be filtered using the <code>facets</code> parameter when
     * retrieving the first page. This value is used for all subsequent pages,
     * (the <code>facets</code> parameter is ignored when retrieving subsequent
     * pages).
     *
     * @param pagingState Used for pagination control.
     *                    To retrieve the first page, use <code>null</code>.
     *                    To retrieve subsequent pages, use the
     *                    <code>pagingState</code> that accompanied the
     *                    previous page.
     * @param items       Specifies the number of items to include in each page.
     *                    This value is only necessary on the retrieval of the
     *                    first page, and will be used for all subsequent
     *                    pages.
     * @param facets      Specifies a comma delimited list of search facet Ids
     *                    to filter the results.
     *                    Pass null or an empty string to not filter.
     * @param userId      Specifies a user UUID identifying the currently logged-in
     *                    user. Will be null for unauthenticated requests.
     * @return Observable<SongList>
     */
    public Observable<SongList> browseSongs(String pagingState, Integer items, String facets, String userId) {
        return Observable.just(songMockData.browseSongs(pagingState, items, facets));
    }

    /**
     * Get data on a song in the catalog.
     *
     * @param songId   Specifies the UUID of the song to retrieve.
     * @param userId   Specifies the UUID of the authenticated user
     * @return Observable<SongInfo>
     */
    public Observable<Optional<SongInfo>> getSong(String songId, String userId) {
        return Observable.just(Optional.of(songMockData.getSong(songId)));
    }

    /**
     * Gets UUID to use as sessionToken on valid credentials
     *
     * @param email    user email
     * @param password user password
     * @return Observable<Optional<UUID>>
     */
    public Observable<Optional<UUID>> logIn(String email, String password) {
        return Observable.just(Optional.of(UUID.fromString(logInMockData.getAuthenticatedFlag(email, password).getAuthenticated())));
    }

}
