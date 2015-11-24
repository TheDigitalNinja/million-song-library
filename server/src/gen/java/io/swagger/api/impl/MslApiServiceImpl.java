package io.swagger.api.impl;

import com.kenzan.msl.server.mock.FacetMockData;
import com.kenzan.msl.server.mock.AlbumMockData;
import com.kenzan.msl.server.mock.ArtistMockData;
import com.kenzan.msl.server.mock.LogInMockData;
import com.kenzan.msl.server.mock.SongMockData;
import com.kenzan.msl.server.services.CassandraCatalogService;
import com.kenzan.msl.server.services.CatalogService;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.MslApiService;
import io.swagger.api.NotFoundException;

import java.math.BigDecimal;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

/*
 * This file (along with MslApiService) is the bridge between the swagger generated code and the rest of the service code.
 */

public class MslApiServiceImpl extends MslApiService {

    // TODO: @Import
//    private CatalogService catalogService = new CassandraCatalogService();

    private AlbumMockData albumMockData = new AlbumMockData();
    private ArtistMockData artistMockData = new ArtistMockData();
    private SongMockData songMockData = new SongMockData();
    private LogInMockData logInMockData = new LogInMockData();
    private FacetMockData facetMockData = new FacetMockData();
    private NewCookie cookie;

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    @Override
    public Response getAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
        if (null == albumId || albumId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'albumId' is null.")).build();
        }
        // TODO replace mock data
        //AlbumInfo albumInfo = catalogService.getAlbum(albumId, null).toBlocking().first();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId))).build();
    }

    @Override
    public Response getAlbumImage(String albumId)
            throws NotFoundException {
        // Validate required parameters
        if (null == albumId || albumId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'albumId' is null.")).build();
        }

        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId).getImageLink())).build();
    }

    @Override
    public Response browseAlbums(Integer items, String pagingState, String facets)
            throws NotFoundException {
        // Validate required parameters
        if (null == items) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'items' is null.")).build();
        }
        // TODO replace mock data
        //AlbumList albumList = catalogService.browseAlbums(pagingState, items, facets, null).toBlocking().first();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.browseAlbums(pagingState, items, facets))).build();
    }

    // ========================================================================================================== ARTIST
    // =================================================================================================================

    @Override
    public Response getArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
        if (null == artistId || artistId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'artistId' is null.")).build();
        }
        // TODO replace mock data
        //ArtistInfo artistInfo = catalogService.getArtist(artistId, null).toBlocking().first();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId))).build();
    }

    @Override
    public Response getArtistImage(String artistId)
            throws NotFoundException {
        // Validate required parameters
        if (null == artistId || artistId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'artistId' is null.")).build();
        }
        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId).getImageLink())).build();
    }

    @Override
    public Response browseArtists(Integer items, String pagingState, String facets)
            throws NotFoundException {
        // Validate required parameters
        if (null == items) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'itmes' is null.")).build();
        }

        // TODO replace mock data
        //ArtistList artistList = catalogService.browseArtists(pagingState, items, facets, null).toBlocking().first();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.browseArtists(pagingState, items, facets))).build();
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    @Override
    public Response getSong(String songId)
            throws NotFoundException {
        // Validate required parameters
        if (null == songId || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }
        // TODO replace mock data
        //SongInfo songInfo = catalogService.getSong(songId, null).toBlocking().first();
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
        // Validate required parameters
        if (null == songId || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }

        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId).getImageLink())).build();
    }

    @Override
    public Response browseSongs(Integer items, String pagingState, String facets)
            throws NotFoundException {
        // Validate required parameters
        if (null == items) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'items' is null.")).build();
        }

        // TODO replace mock data
        // SongList songList = catalogService.browseSongs(pagingState, items, facets, null).toBlocking().first();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.browseSongs(pagingState, items, facets))).build();
    }

    @Override
    public Response playSong(String songId)
            throws NotFoundException {
        // Validate required parameters
        if (null == songId || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }
        // do some magic !
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response commentSong(String songId)
            throws NotFoundException {
        if (null == songId || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response rateArtist(String artistId, Integer rating)
            throws NotFoundException {

        if (null == artistId || artistId.isEmpty() || rating == null) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Bad Request")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response rateAlbum(String albumId, Integer rating)
            throws NotFoundException {

        if (null == albumId || albumId.isEmpty() || rating == null) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Bad Request")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response rateSong(String songId, Integer rating)
            throws NotFoundException {

        if (null == songId || songId.isEmpty() || rating == null) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Bad Request")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
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
        // Validate required parameters
        if (null == email || email.isEmpty() || null == password || password.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Bad Request")).build();
        }

        // TODO replace current mock data
        MslSessionToken.value = email;
        cookie = new NewCookie("sessionToken", MslSessionToken.value);
        return Response.ok()
                .header("Set-Cookie", cookie.toString() + "; Domain=local.msl.dev ; Path=/; HttpOnly")
                .entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", logInMockData.getAuthenticatedFlag(email, password))).build();
    }

    @Override
    public Response logout()
            throws NotFoundException {
        // do some magic!
        MslSessionToken.value = "";
        cookie = new NewCookie("sessionToken", MslSessionToken.value);
        return Response.ok()
                .header("Set-Cookie", cookie.toString() + ";Max-Age=0; Domain=local.msl.dev; Path=/; HttpOnly")
                .entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", logInMockData.logOut())).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // Validate required parameters
        if (null == email || email.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'email' is null.")).build();
        }

        // do some magic!
        return Response.ok()
                .entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ----------------------------------------------------------------------------------------------------------- OTHER
    // =================================================================================================================

    @Override
    public Response getFacet(String facetId)
            throws NotFoundException {
        // Validate required parameters
        if (null == facetId || facetId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'facetId' is null.")).build();
        }
        // TODO replace mock data
        //FacetInfoWithChildren facetInfoWithChildren = catalogService.getFacet(facetId).toBlocking().first();
        //return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", facetInfoWithChildren)).build();
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", facetMockData.getFacet(facetId))).build();
    }


    @Override
    public Response searchFor(String searchText, String searchType)
            throws NotFoundException {
        // Validate required parameters
        if (null == searchText || searchText.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'searchText' is null.")).build();
        }
        if (null == searchType || searchType.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'searchType' is null.")).build();
        }

        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response removeSong(String songId)
            throws NotFoundException {

        if (songId == null || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response removeArtist(String artistId)
            throws NotFoundException {

        if (artistId == null || artistId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'artistId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response removeAlbum(String albumId)
            throws NotFoundException {

        if (albumId == null || albumId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'albumId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response addAlbum(String albumId)
            throws NotFoundException {

        if (albumId == null || albumId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'albumId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response addArtist(String artistId)
            throws NotFoundException {

        if (artistId == null || artistId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'artistId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

    @Override
    public Response addSong(String songId)
            throws NotFoundException {

        if (songId == null || songId.isEmpty()) {
            return Response.status(400).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'songId' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
        } else {
            return Response.status(401).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
    }

}
