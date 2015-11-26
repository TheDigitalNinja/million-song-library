package io.swagger.api.impl;

import io.swagger.api.*;
import io.swagger.mock.*;
import io.swagger.model.*;

import com.sun.jersey.multipart.FormDataParam;

import io.swagger.model.MyLibrary;
import io.swagger.model.ErrorResponse;
import io.swagger.model.UserInfo;
import io.swagger.model.SongList;
import io.swagger.model.AlbumInfo;
import io.swagger.model.NotFoundResponse;
import io.swagger.model.ArtistInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistList;
import io.swagger.model.FacetInfoWithChildren;
import io.swagger.model.SongInfo;
import java.io.File;
import io.swagger.model.LoginSuccessResponse;
import io.swagger.model.StatusResponse;
import java.math.BigDecimal;
import io.swagger.model.SearchResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-04T16:53:15.265-07:00")
public class MslApiServiceImpl extends MslApiService {

    private AlbumMockData albumMockData = new AlbumMockData();
    private ArtistMockData artistMockData = new ArtistMockData();
    private SongMockData songMockData = new SongMockData();
    private FacetMockData facetMockData = new FacetMockData();
    private LogInMockData logInMockData = new LogInMockData();

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    @Override
    public Response getAlbum(String albumId)
            throws NotFoundException {
        // TODO getAlbum actual method
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId))).build();
    }

    @Override
    public Response getAlbumImage(String albumId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId).getImageLink())).build();
    }

    @Override
    public Response browseAlbums(String pagingState, Integer items, String facets)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.browseAlbums(pagingState, items, facets))).build();
    }

    // ========================================================================================================== ARTIST
    // =================================================================================================================

    @Override
    public Response getArtist(String artistId)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId))).build();
    }

    @Override
    public Response getArtistImage(String artistId)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId).getImageLink())).build();
    }

    @Override
    public Response browseArtists(String pagingState, Integer items, String facets)
            throws NotFoundException {
        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.browseArtists(pagingState, items, facets))).build();
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    @Override
    public Response getSong(String songId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId))).build();
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
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId).getImageLink())).build();
    }

    @Override
    public Response browseSongs(String pagingState, Integer items, String facets)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.browseSongs(pagingState, items, facets))).build();
    }

    @Override
    public Response playSong(String songId)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response commentSong(String songId, String sessionToken)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response rateArtist(String artistId, BigDecimal rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response rateAlbum(String albumId, BigDecimal rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response rateSong(String songId, BigDecimal rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
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
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", logInMockData.getSessionToken(email, password))).build();
    }

    @Override
    public Response logout()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", logInMockData.logOut())).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ----------------------------------------------------------------------------------------------------------- OTHER
    // =================================================================================================================

    @Override
    public Response getFacet(String facetId)
            throws NotFoundException {
        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", facetMockData.getFacet(facetId))).build();
    }


    @Override
    public Response searchFor(String searchText, String searchType)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response addSong(String songId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response addAlbum(String albumId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response addArtist(String artistId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response removeSong(String songId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response removeAlbum(String albumId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

    @Override
    public Response removeArtist(String artistId, String sessionToken)
            throws NotFoundException {

        if (sessionToken == null || sessionToken.isEmpty()){
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        } else {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        }
    }

}
