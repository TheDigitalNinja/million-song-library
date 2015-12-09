package io.swagger.api.impl;

import com.google.common.base.Optional;
import com.kenzan.msl.server.manager.FacetManager;
import com.kenzan.msl.server.services.AuthenticationService;

import io.swagger.api.ApiResponseMessage;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.ErrorResponse;
import io.swagger.model.NotFoundResponse;

import io.swagger.model.SongInfo;
import io.swagger.model.SongList;
import io.swagger.model.MyLibrary;

import com.kenzan.msl.server.mock.AlbumMockData;
import com.kenzan.msl.server.mock.ArtistMockData;
import com.kenzan.msl.server.mock.SongMockData;
import com.kenzan.msl.server.services.CassandraCatalogService;
import com.kenzan.msl.server.services.CatalogService;

import io.swagger.api.MslApiService;
import io.swagger.api.NotFoundException;

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

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    @Override
    public Response getAlbum(String albumId)
            throws NotFoundException {
    	// Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

    	Optional<AlbumInfo> optAlbumInfo;
    	try {
    		optAlbumInfo = catalogService.getAlbum(albumId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (!optAlbumInfo.isPresent()) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find album with id=" + albumId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}
        
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", optAlbumInfo.get())).build();
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

    	Optional<ArtistInfo> optArtistInfo;
    	try {
    		optArtistInfo = catalogService.getArtist(artistId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (!optArtistInfo.isPresent()) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find artist with id=" + artistId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", optArtistInfo.get())).build();
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

        Optional<SongInfo> optSongInfo;
    	try {
    		optSongInfo = catalogService.getSong(songId, null).toBlocking().first();
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		ErrorResponse errorResponse = new ErrorResponse();
    		errorResponse.setMessage("Server error: " + e.getMessage());
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
    	}
    	
    	if (!optSongInfo.isPresent()) {
    		NotFoundResponse notFoundResponse = new NotFoundResponse();
    		notFoundResponse.setMessage("Unable to find song with id=" + songId);
    		return Response.status(Response.Status.NOT_FOUND).entity(notFoundResponse).build();
    	}

        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", optSongInfo.get())).build();
    }

    @Override
    public Response getRecentSongs()
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
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

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
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

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
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

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
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

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no valid sessionToken provided")).build();
    }

    // ========================================================================================================= LIBRARY
    // =================================================================================================================

    @Override
    public Response getMyLibrary()
            throws NotFoundException {
        if (AuthenticationService.hasValidToken()) {

            MyLibrary myLibrary;
            try {
                myLibrary = catalogService.getMyLibrary(MslSessionToken.getInstance().getTokenValue()).toBlocking().first();
                return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "success", myLibrary)).build();
            }
            catch (Exception e) {
                e.printStackTrace();

                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setMessage("Server error: " + e.getMessage());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no valid sessionToken provided")).build();
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
    public Response login(String email, String password) throws NotFoundException {
        return AuthenticationService.logIn(catalogService, email, password);
    }

    @Override
    public Response logout() throws NotFoundException {
        return AuthenticationService.logOut();
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

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response removeAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addAlbum(String albumId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(albumId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'albumId' is null or empty.")).build();
    	}

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addArtist(String artistId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(artistId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'artistId' is null or empty.")).build();
    	}

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response addSong(String songId)
            throws NotFoundException {
        // Validate required parameters
    	if (StringUtils.isEmpty(songId)) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "Required parameter 'songId' is null or empty.")).build();
    	}

        if (AuthenticationService.hasValidToken()) {
            return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
        }
		return Response.status(Response.Status.UNAUTHORIZED).entity(new MslApiResponseMessage(MslApiResponseMessage.ERROR, "no sessionToken provided")).build();
    }

    @Override
    public Response registration(String username, String password, String confirmationPassword)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new MslApiResponseMessage(MslApiResponseMessage.OK, "magic!")).build();
    }
}
