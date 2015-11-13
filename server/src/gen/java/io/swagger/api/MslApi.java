package io.swagger.api;

import io.swagger.model.*;
import io.swagger.api.MslApiService;
import io.swagger.api.factories.MslApiServiceFactory;

import io.swagger.annotations.ApiParam;

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
import javax.ws.rs.*;

@Path("/msl")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@io.swagger.annotations.Api(value = "/msl", description = "the msl API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-10T16:56:12.664-06:00")
public class MslApi  {

   private final MslApiService delegate = MslApiServiceFactory.getMslApi();

    @GET
    @Path("/v1/accountedge/users/mylibrary")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get the user's library of favorites", response = MyLibrary.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Retrieved user library", response = MyLibrary.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = MyLibrary.class) })

    public Response getMyLibrary()
    throws NotFoundException {
        return delegate.getMyLibrary();
    }
    @GET
    @Path("/v1/accountedge/users/profile")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get the current user's account info", response = UserInfo.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = UserInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = UserInfo.class) })

    public Response getUserInfo()
    throws NotFoundException {
        return delegate.getUserInfo();
    }
    @GET
    @Path("/v1/accountedge/users/recentsongs")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get recently played songs", response = SongList.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Got recent songs!", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongList.class) })

    public Response getRecentSongs()
    throws NotFoundException {
        return delegate.getRecentSongs();
    }
    @GET
    @Path("/v1/catalogedge/album/{albumId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get data for an Album of songs", response = AlbumInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = AlbumInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Album not found", response = AlbumInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = AlbumInfo.class) })

    public Response getAlbum(@ApiParam(value = "Album identification number",required=true) @PathParam("albumId") String albumId)
    throws NotFoundException {
        return delegate.getAlbum(albumId);
    }
    @GET
    @Path("/v1/catalogedge/artist/{artistId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get data on an Artist", response = ArtistInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = ArtistInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Artist not found", response = ArtistInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = ArtistInfo.class) })

    public Response getArtist(@ApiParam(value = "Artist Identifier",required=true) @PathParam("artistId") String artistId)
    throws NotFoundException {
        return delegate.getArtist(artistId);
    }
    @GET
    @Path("/v1/catalogedge/browse/album")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for albums in the catalog. The sort order is predetermined: if one or more facets are passed then the order will be alphabetical ascending by album name, if no facets are passed then the results will be in featured order.", response = AlbumList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = AlbumList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pagingState. The string passed as pagingState is not a valid pagingState key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = AlbumList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = AlbumList.class) })

    public Response browseAlbums(@ApiParam(value = "PagingState is used to retrieve the next page in a paginated query. A  PagingState instance contains a UUID key to an object in Cassandra on the server. The data within the object allows the server to retrieve the next page of data.") @QueryParam("pagingState") String pagingState,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facet IDs to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets)
    throws NotFoundException {
        return delegate.browseAlbums(pagingState,items,facets);
    }
    @GET
    @Path("/v1/catalogedge/browse/artist")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for artists in the catalog. The sort order is predetermined: if one or more facets are passed then the order will be alphabetical ascending by artist name, if no facets are passed then the results will be in featured order.", response = ArtistList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = ArtistList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pagingState. The string passed as pagingState is not a valid pagingState key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = ArtistList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = ArtistList.class) })

    public Response browseArtists(@ApiParam(value = "PagingState is used to retrieve the next page in a paginated query. A  PagingState instance contains a UUID key to an object in Cassandra on the server. The data within the object allows the server to retrieve the next page of data.") @QueryParam("pagingState") String pagingState,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facet IDs to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets)
    throws NotFoundException {
        return delegate.browseArtists(pagingState,items,facets);
    }
    @GET
    @Path("/v1/catalogedge/browse/song")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get browsing data for songs in the catalog. The sort order is predetermined: if one or more facets are passed then the order will be alphabetical ascending by song name, if no facets are passed then the results will be in featured order.", response = SongList.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Request parameter error: (1) Invalid pagingState. The string passed as pagingState is not a valid pagingState key. (2) Invalid facets. The string passed as facets is not a valid comma delimited list of valid facet keys.", response = SongList.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongList.class) })

    public Response browseSongs(@ApiParam(value = "PagingState is used to retrieve the next page in a paginated query. A  PagingState instance contains a UUID key to an object in Cassandra on the server. The data within the object allows the server to retrieve the next page of data.") @QueryParam("pagingState") String pagingState,
    @ApiParam(value = "Number of items to return. Used for paginating results. The maximum is 100. If not provided, or falls outside the inclusive range 1-100, defaults to 25.") @QueryParam("items") Integer items,
    @ApiParam(value = "Comma delimited list of facet IDs to use as filters. If not provided, no filters will be applied.") @QueryParam("facets") String facets)
    throws NotFoundException {
        return delegate.browseSongs(pagingState,items,facets);
    }
    @GET
    @Path("/v1/catalogedge/facet/{facetId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a facet in the heirarchy. Passing a facetId of '~' retrieves the root facet.", response = FacetInfoWithChildren.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = FacetInfoWithChildren.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Parent facet not found", response = FacetInfoWithChildren.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = FacetInfoWithChildren.class) })

    public Response getFacet(@ApiParam(value = "Facet identifier",required=true) @PathParam("facetId") String facetId)
    throws NotFoundException {
        return delegate.getFacet(facetId);
    }
    @GET
    @Path("/v1/catalogedge/song/{songId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a song's info from the database", response = SongInfo.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = SongInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = SongInfo.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = SongInfo.class) })

    public Response getSong(@ApiParam(value = "The song's identification number",required=true) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.getSong(songId);
    }
    @GET
    @Path("/v1/imageedge/album/{albumId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get an album cover image. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired album cover image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getAlbumImage(@ApiParam(value = "Album id",required=true) @PathParam("albumId") String albumId)
    throws NotFoundException {
        return delegate.getAlbumImage(albumId);
    }
    @GET
    @Path("/v1/imageedge/artist/{artistId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get an artist's picture. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired artist image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getArtistImage(@ApiParam(value = "Artist Identifier",required=true) @PathParam("artistId") String artistId)
    throws NotFoundException {
        return delegate.getArtistImage(artistId);
    }
    @GET
    @Path("/v1/imageedge/song/{songId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Get a song cover. Result will be an image (png, jpg, etc.).", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully acquired song cover image", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Image not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response getSongImage(@ApiParam(value = "Song id",required=true) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.getSongImage(songId);
    }
    @POST
    @Path("/v1/loginedge/login")
    @Consumes({ "application/x-www-form-urlencoded" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Logs a user into the system", response = LoginSuccessResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully logged on the user", response = LoginSuccessResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Invalid Login Credentials", response = LoginSuccessResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = LoginSuccessResponse.class) })

    public Response login(@ApiParam(value = "Login to the app with user credentials", required=true)@FormParam("email")  String email,
    @ApiParam(value = "user password", required=true)@FormParam("password")  String password)
    throws NotFoundException {
        return delegate.login(email,password);
    }
    @POST
    @Path("/v1/loginedge/logout")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Logs a user out of the system", response = StatusResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Logged out", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response logout()
    throws NotFoundException {
        return delegate.logout();
    }
    @POST
    @Path("/v1/loginedge/resetpassword")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Resets the user's password via email", response = StatusResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Success", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response resetPassword(@ApiParam(value = "Email used to identify the account", required=true)@FormParam("email")  String email)
    throws NotFoundException {
        return delegate.resetPassword(email);
    }
    @GET
    @Path("/v1/playeredge/play/{songId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Request to begin streaming a song. Result will be an mp3.", response = File.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Request accepted streaming sound data", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = File.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = File.class) })

    public Response playSong(@ApiParam(value = "Song id for the song the user requested",required=true) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.playSong(songId);
    }
    @PUT
    @Path("/v1/ratingsedge/commentsong/{songId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Make a comment about a song", response = StatusResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully sent comment data to the server", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response commentSong(@ApiParam(value = "Id of the song you want to comment on",required=true) @PathParam("songId") String songId)
    throws NotFoundException {
        return delegate.commentSong(songId);
    }
    @PUT
    @Path("/v1/ratingsedge/ratesong/{songId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Update your rating of a song.", response = StatusResponse.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "session_id")
    })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully sent rating data to the server", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Song not found", response = StatusResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Server Error", response = StatusResponse.class) })

    public Response rateSong(@ApiParam(value = "Id for the song that is being rated",required=true) @PathParam("songId") String songId,
    @ApiParam(value = "Value between 1-5 that represents the user's rating of the song", required=true)@FormParam("rating")  BigDecimal rating)
    throws NotFoundException {
        return delegate.rateSong(songId,rating);
    }
    @GET
    @Path("/v1/searchedge/search")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "", notes = "Search things that match a user provided string", response = SearchResponse.class)
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Successfully searched", response = SearchResponse.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Bad Request", response = SearchResponse.class) })

    public Response searchFor(@ApiParam(value = "User search string",required=true) @QueryParam("searchText") String searchText,
    @ApiParam(value = "A string representation of the various types of entities that can be searched.",required=true, allowableValues="album, artist, song") @QueryParam("searchType") String searchType)
    throws NotFoundException {
        return delegate.searchFor(searchText,searchType);
    }
}

