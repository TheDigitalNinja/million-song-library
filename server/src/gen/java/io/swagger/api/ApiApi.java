package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.ApiApiService;
import io.swagger.api.factories.ApiApiServiceFactory;

import io.swagger.annotations.ApiParam;

import com.sun.jersey.multipart.FormDataParam;

import io.swagger.model.ErrorResponse;
import io.swagger.model.SongList;
import io.swagger.model.UserInfo;
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
import io.swagger.model.SessionInfo;
import java.math.BigDecimal;
import io.swagger.model.SearchResponse;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.ws.rs.core.Response;
import javax.ws.rs.*;

@Path("/api")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/api", description = "the api API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class ApiApi  {

   private final ApiApiService delegate = ApiApiServiceFactory.getApiApi();

    @GET
    @Path("/v1/accountedge/users/mylibrary")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get the user's library of songs", response = SongList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Retrieved user library", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongList.class) })

    public Response getMyLibrary()
    throws NotFoundException {
        return delegate.getMyLibrary();
    }
    @GET
    @Path("/v1/accountedge/users/profile")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get the current user's account info", response = UserInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = UserInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = UserInfo.class) })

    public Response getUserInfo()
    throws NotFoundException {
        return delegate.getUserInfo();
    }
    @GET
    @Path("/v1/accountedge/users/recentsongs")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get recently played songs", response = SongList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Got recent songs!", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongList.class) })

    public Response getRecentSongs()
    throws NotFoundException {
        return delegate.getRecentSongs();
    }
    @GET
    @Path("/v1/catalogedge/album/{albumId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get data for an Album of songs", response = AlbumInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = AlbumInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Album not found", response = AlbumInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = AlbumInfo.class) })

    public Response getAlbum(@ApiParam(value = "Album identification number",required=true ) @PathParam("albumId") String albumId)
    throws NotFoundException {
        return delegate.getAlbum(albumId);
    }
    @GET
    @Path("/v1/catalogedge/artist/{artistId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get data on an Artist", response = ArtistInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = ArtistInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Artist not found", response = ArtistInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = ArtistInfo.class) })

    public Response getArtist(@ApiParam(value = "Artist Identifier",required=true ) @PathParam("artistId") String artistId)
    throws NotFoundException {
        return delegate.getArtist(artistId);
    }
    @GET
    @Path("/v1/catalogedge/browse/album")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for albums in the catalog", response = AlbumList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = AlbumList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pos. The string passed as pos is not a valid pos key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = AlbumList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = AlbumList.class) })

    public Response browseAlbums(@ApiParam(value = "Used for paginating results. Start with next album after this position. If not provided, defaults to the beginning. This string is sourced from the \"last_pos\" field in the server response from the previous album page.") @QueryParam("pos") String pos,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facets to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets,
    @ApiParam(value = "Comma delimited list of fields on which to sort. If not provided, results will be sorted by album name.") @QueryParam("sort_fields") String sortFields)
    throws NotFoundException {
        return delegate.browseAlbums(pos,items,facets,sortFields);
    }
    @GET
    @Path("/v1/catalogedge/browse/artist")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for artists in the catalog", response = ArtistList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = ArtistList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pos. The string passed as pos is not a valid pos key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = ArtistList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = ArtistList.class) })

    public Response browseArtists(@ApiParam(value = "Used for paginating results. Start with next artist after this position. If not provided, defaults to the beginning. This string is sourced from the \"last_pos\" field in the server response from the previous artist page.") @QueryParam("pos") String pos,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facets to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets,
    @ApiParam(value = "Comma delimited list of fields on which to sort. If not provided, results will be sorted by artist name.") @QueryParam("sort_fields") String sortFields)
    throws NotFoundException {
        return delegate.browseArtists(pos,items,facets,sortFields);
    }
    @GET
    @Path("/v1/catalogedge/browse/song")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for songs in the catalog", response = SongList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pos. The string passed as pos is not a valid pos key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongList.class) })

    public Response browseSongs(@ApiParam(value = "Used for paginating results. Start with next song after this position. If not provided, defaults to the beginning. This string is sourced from the \"last_pos\" field in the server response from the previous song page.") @QueryParam("pos") String pos,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facets to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets,
    @ApiParam(value = "Comma delimited list of fields on which to sort. If not provided, results will be sorted by song name.") @QueryParam("sort_fields") String sortFields)
    throws NotFoundException {
        return delegate.browseSongs(pos,items,facets,sortFields);
    }
    @GET
    @Path("/v1/catalogedge/facet/{facetId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a facet in the heirarchy. Passing a facetId of \" \" retrieves the root facet.", response = FacetInfoWithChildren.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = FacetInfoWithChildren.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Parent facet not found", response = FacetInfoWithChildren.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = FacetInfoWithChildren.class) })

    public Response getFacet(@ApiParam(value = "Facet identifier",required=true ) @PathParam("facetId") String facetId)
    throws NotFoundException {
        return delegate.getFacet(facetId);
    }
    @GET
    @Path("/v1/catalogedge/song/{songId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a song's info from the database", response = SongInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SongInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = SongInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongInfo.class) })

    public Response getSong(@ApiParam(value = "The song's identification number",required=true ) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.getSong(songId);
    }
    @GET
    @Path("/v1/imageedge/album/{albumId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get an album cover image. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired album cover image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getAlbumImage(@ApiParam(value = "Album id",required=true ) @PathParam("albumId") String albumId)
    throws NotFoundException {
        return delegate.getAlbumImage(albumId);
    }
    @GET
    @Path("/v1/imageedge/artist/{artistId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get an artist's picture. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired artist image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getArtistImage(@ApiParam(value = "Artist Identifier",required=true ) @PathParam("artistId") String artistId)
    throws NotFoundException {
        return delegate.getArtistImage(artistId);
    }
    @GET
    @Path("/v1/imageedge/song/{songId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a song cover. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired song cover image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getSongImage(@ApiParam(value = "Song id",required=true ) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.getSongImage(songId);
    }
    @POST
    @Path("/v1/loginedge/login")
    @Consumes({ "application/x-www-form-urlencoded" })
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Logs a user into the system", response = LoginSuccessResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully logged on the user", response = LoginSuccessResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Invalid Login Credentials", response = LoginSuccessResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = LoginSuccessResponse.class) })

    public Response login(@ApiParam(value = "Login to the app with user credentials", required=true )@FormParam("email")  String email,
    @ApiParam(value = "user password", required=true )@FormParam("password")  String password)
    throws NotFoundException {
        return delegate.login(email,password);
    }
    @POST
    @Path("/v1/loginedge/logout")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Logs a user out of the system", response = StatusResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Logged out", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response logout()
    throws NotFoundException {
        return delegate.logout();
    }
    @POST
    @Path("/v1/loginedge/resetpassword")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Resets the user's password via email", response = StatusResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response resetPassword(@ApiParam(value = "Email used to identify the account", required=true )@FormParam("email")  String email)
    throws NotFoundException {
        return delegate.resetPassword(email);
    }
    @GET
    @Path("/v1/loginedge/sessioninfo")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Endpoint for api services to get session data from session token", response = SessionInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SessionInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SessionInfo.class) })

    public Response getSessionInfo()
    throws NotFoundException {
        return delegate.getSessionInfo();
    }
    @GET
    @Path("/v1/playeredge/play/{songId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Request to begin streaming a song. Result will be an mp3.", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Request accepted streaming sound data", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response playSong(@ApiParam(value = "Song id for the song the user requested",required=true ) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.playSong(songId);
    }
    @PUT
    @Path("/v1/ratingsedge/commentsong/{songId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Make a comment about a song", response = StatusResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully sent comment data to the server", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response commentSong(@ApiParam(value = "Id of the song you want to comment on",required=true ) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.commentSong(songId);
    }
    @PUT
    @Path("/v1/ratingsedge/ratesong/{songId}")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Update your rating of a song.", response = StatusResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully sent rating data to the server", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response rateSong(@ApiParam(value = "Id for the song that is being rated",required=true ) @PathParam("songId") String songId,
    @ApiParam(value = "Value between 1-5 that represents the user's rating of the song", required=true )@FormParam("rating")  BigDecimal rating)
    throws NotFoundException {
        return delegate.rateSong(songId,rating);
    }
    @GET
    @Path("/v1/searchedge/search")
    
    
    @io.swagger.annotations.ApiOperation(value = "", notes = "Search things that match a user provided string", response = SearchResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully searched", response = SearchResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Bad Request", response = SearchResponse.class) })

    public Response searchFor(
            @ApiParam(value = "User search string", required=true )
            @QueryParam("searchText")  String searchText,
            @ApiParam(value = "type of thing to search for Album, Artist, Song")
            @QueryParam("searchType") String searchType
    ) throws NotFoundException {
        return delegate.searchFor(searchText,searchType);
    }
}

