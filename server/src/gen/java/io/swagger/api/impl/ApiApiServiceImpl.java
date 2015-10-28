package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.mock.ArtistMockData;
import io.swagger.mock.FacetMockData;
import io.swagger.mock.SongMockData;
import io.swagger.model.*;
import io.swagger.mock.AlbumMockData;

import java.math.BigDecimal;

import io.swagger.api.NotFoundException;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-19T15:56:09.144-06:00")
public class ApiApiServiceImpl extends ApiApiService {

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    private AlbumMockData albumMockData = new AlbumMockData();

    @Override
    public Response getAlbum(String albumId)
            throws NotFoundException {
        // TODO getAlbum actual method
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId))).build();
    }

    @Override
    public Response getAlbumImage(String albumId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId).getImageLink())).build();
    }

    @Override
    public Response browseAlbums(String pagingState, Integer items, String facets, String sortFields)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.browseAlbums(pagingState, items, facets, sortFields))).build();
    }

    // ========================================================================================================== ARTIST
    // =================================================================================================================

    private ArtistMockData artistMockData = new ArtistMockData();

    @Override
    public Response getArtist(String artistId)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId))).build();
    }

    @Override
    public Response getArtistImage(String artistId)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId).getImageLink())).build();
    }

    @Override
    public Response browseArtists(String pagingState, Integer items, String facets, String sortFields)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.browseArtists(pagingState, items, facets, sortFields))).build();
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    private SongMockData songMockData = new SongMockData();

    @Override
    public Response getSong(String songId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId))).build();
    }

    @Override
    public Response getRecentSongs()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getSongImage(String songId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId).getImageLink())).build();
    }

    @Override
    public Response browseSongs(String pagingState, Integer items, String facets, String sortFields)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.browseSongs(pagingState, items, facets, sortFields))).build();
    }

    @Override
    public Response playSong(String songId)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response commentSong(String songId)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response rateSong(String songId, BigDecimal rating)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ========================================================================================================= LIBRARY
    // =================================================================================================================

    @Override
    public Response getMyLibrary()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ========================================================================================================= SESSION
    // =================================================================================================================

    @Override
    public Response getUserInfo()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response login(String email, String password)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response logout()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getSessionInfo()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ----------------------------------------------------------------------------------------------------------- OTHER
    // =================================================================================================================

    FacetMockData facetMockData = new FacetMockData();

    @Override
    public Response getFacet(String facetId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "success", facetMockData.getFacet(facetId))).build();
    }


    @Override
    public Response searchFor(String searchText, String searchType)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

}
