package io.swagger.api.impl;

import com.kenzan.msl.server.manager.FacetManager;
import com.kenzan.msl.server.mock.FacetMockData;

import io.swagger.api.ApiResponseMessage;
import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.ErrorResponse;

import io.swagger.model.NotFoundResponse;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import com.kenzan.msl.server.mock.AlbumMockData;
import com.kenzan.msl.server.mock.ArtistMockData;
import com.kenzan.msl.server.mock.LogInMockData;
import com.kenzan.msl.server.mock.SongMockData;
import com.kenzan.msl.server.services.CassandraCatalogService;
import com.kenzan.msl.server.services.CatalogService;

import io.swagger.api.MslApiService;
import io.swagger.api.NotFoundException;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;

/*
 * This file (along with MslApiService) is the bridge between the swagger generated code and the rest of the service code.
 */

public class MslApiServiceImpl extends MslApiService {

    // TODO: @Inject
    private CatalogService catalogService = new CassandraCatalogService();

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
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

    	AlbumInfo albumInfo;
    	try {
    		albumInfo = catalogService.getAlbum(albumId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (null == albumInfo) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find album with id=" + albumId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}
        
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", albumInfo)).build();
    }

    @Override
    public Response getAlbumImage(String albumId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId).getImageLink())).build();
    }

    @Override
    public Response browseAlbums(Integer items, String pagingState, String facets)
            throws NotFoundException {
        AlbumList albumList;
    	try {
    		albumList = catalogService.browseAlbums(pagingState, items, facets, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();

    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", albumList)).build();
    }

    // ========================================================================================================== ARTIST
    // =================================================================================================================

    @Override
    public Response getArtist(String artistId)
            throws NotFoundException {
    	// Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}

    	ArtistInfo artistInfo;
    	try {
    		artistInfo = catalogService.getArtist(artistId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (null == artistInfo) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find artist with id=" + artistId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", artistInfo)).build();
    }

    @Override
    public Response getArtistImage(String artistId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}
    	
        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", artistMockData.getArtist(artistId).getImageLink())).build();
    }

    @Override
    public Response browseArtists(Integer items, String pagingState, String facets)
            throws NotFoundException {
        ArtistList artistList;
    	try {
    		artistList = catalogService.browseArtists(pagingState, items, facets, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", artistList)).build();
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    @Override
    public Response getSong(String songId)
            throws NotFoundException {
    	// Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        SongInfo songInfo;
    	try {
    		songInfo = catalogService.getSong(songId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (null == songInfo) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find song with id=" + songId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", songInfo)).build();
    }

    @Override
    public Response getRecentSongs()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getSongImage(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", songMockData.getSong(songId).getImageLink())).build();
    }

    @Override
    public Response browseSongs(Integer items, String pagingState, String facets)
            throws NotFoundException {
        SongList songList;
    	try {
    		songList = catalogService.browseSongs(pagingState, items, facets, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", songList)).build();
    }

    @Override
    public Response playSong(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

    	// do some magic !
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response commentSong(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response rateArtist(String artistId, Integer rating)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}
        if (null == rating) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'rating' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response rateAlbum(String albumId, Integer rating)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}
        if (null == rating) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'rating' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response rateSong(String songId, Integer rating)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}
        if (null == rating) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'rating' is null.")).build();
        }

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    // ========================================================================================================= LIBRARY
    // =================================================================================================================

    @Override
    public Response getMyLibrary()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    // ========================================================================================================= SESSION
    // =================================================================================================================

    @Override
    public Response getUserInfo()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response login(String email, String password)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'email' is null or empty.")).build();
    	}
    	if (StringUtils.isEmpty(password)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'password' is null or empty.")).build();
    	}

        // TODO replace current mock data
        MslSessionToken.value = email;
        cookie = new NewCookie("sessionToken", MslSessionToken.value);
        return Response.ok()
                .header("Set-Cookie", cookie.toString() + "; Domain=local.msl.dev ; Path=/; HttpOnly")
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", logInMockData.getAuthenticatedFlag(email, password))).build();
    }

    @Override
    public Response logout()
            throws NotFoundException {
        // do some magic!
        MslSessionToken.value = "";
        cookie = new NewCookie("sessionToken", MslSessionToken.value);
        return Response.ok()
                .header("Set-Cookie", cookie.toString() + ";Max-Age=0; Domain=local.msl.dev; Path=/; HttpOnly")
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", logInMockData.logOut())).build();
    }

    @Override
    public Response resetPassword(String email)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(email)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'email' is null or empty.")).build();
    	}

        // do some magic!
        return Response.ok()
                .entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    // ----------------------------------------------------------------------------------------------------------- OTHER
    // =================================================================================================================
    @Override
    public Response getFacet(String facetId)
            throws NotFoundException {
        // Validate required parameters
        if (StringUtils.isEmpty(facetId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "Required parameter 'facetId' is null or empty.")).build();
        }
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", FacetManager.getInstance().getRestFacets(facetId))).build();
    }


    @Override
    public Response searchFor(String searchText, String searchType)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(searchText)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'searchText' is null or empty.")).build();
    	}
    	if (StringUtils.isEmpty(searchType)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'searchType' is null or empty.")).build();
    	}

        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response removeSong(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addSong(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        if (MslSessionToken.isValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
    	// TODO Use Response.Status.x constant instead of hard coded number
		return Response.status(401).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

}
