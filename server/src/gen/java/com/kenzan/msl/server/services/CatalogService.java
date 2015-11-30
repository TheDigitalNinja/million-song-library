/*
 * Copyright 2015, Kenzan,  All rights reserved.
 */
package com.kenzan.msl.server.services;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.FacetInfoWithChildren;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import rx.Observable;

/**
 *
 *
 * @author billschwanitz
 */
public interface CatalogService {

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

	/*
	 * Get browsing data for albums in the catalog.
	 */
	Observable<AlbumList> browseAlbums(String pagingState, Integer items, String facets, String userId);

	/*
	 * Get data on an album in the catalog.
	 */
	Observable<AlbumInfo> getAlbum(String albumId, String userId);


    // ========================================================================================================= ARTISTS
    // =================================================================================================================

	/*
	 * Get browsing data for artists in the catalog.
	 */
	Observable<ArtistList> browseArtists(String pagingState, Integer items, String facets, String userId);

	/*
	 * Get data on an artist in the catalog.
	 */
	Observable<ArtistInfo> getArtist(String artistId, String userId);


    // ========================================================================================================== FACETS
    // =================================================================================================================

	/*
	 * Get data on a search facet in the catalog.
	 */
	Observable<FacetInfoWithChildren> getFacet(String facetId);


    // =========================================================================================================== SONGS
    // =================================================================================================================

	/*
	 * Get browsing data for songs in the catalog.
	 */
	Observable<SongList> browseSongs(String pagingState, Integer items, String facets, String userId);

	/*
	 * Get data on a song in the catalog.
	 */
	Observable<SongInfo> getSong(String songId, String userId);

}
