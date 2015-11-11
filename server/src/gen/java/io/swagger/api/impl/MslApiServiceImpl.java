package io.swagger.api.impl;

import io.swagger.model.AlbumInfo;
import io.swagger.model.AlbumList;
import io.swagger.model.ArtistInfo;
import io.swagger.model.ArtistList;
import io.swagger.model.FacetInfoWithChildren;
import io.swagger.model.SongInfo;
import io.swagger.model.SongList;

import com.kenzan.msl.server.mock.AlbumMockData;
import com.kenzan.msl.server.mock.ArtistMockData;
import com.kenzan.msl.server.mock.LogInMockData;
import com.kenzan.msl.server.mock.SongMockData;
import com.kenzan.msl.server.services.CassandraCatalogService;
import com.kenzan.msl.server.services.CatalogService;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.MslApiService;
import io.swagger.api.NotFoundException;

import javax.ws.rs.core.Response;

/*
 * This file (along with MslApiService) is the bridge between the swagger generated code and the rest of the service code.
 */

public class MslApiServiceImpl extends MslApiService {

	// TODO: @Import
	private CatalogService catalogService = new CassandraCatalogService();
	
    private AlbumMockData albumMockData = new AlbumMockData();
    private ArtistMockData artistMockData = new ArtistMockData();
    private SongMockData songMockData = new SongMockData();
    private LogInMockData logInMockData = new LogInMockData();

    // ========================================================================================================== ALBUMS
    // =================================================================================================================

    @Override
    public Response getAlbum(String albumId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == albumId) {
    	    throw new IllegalArgumentException("Required parameter 'albumId' is null.");
    	}

    	AlbumInfo albumInfo = catalogService.getAlbum(albumId, null).toBlocking().first();
        
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumInfo)).build();
    }

    @Override
    public Response getAlbumImage(String albumId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == albumId) {
    	    throw new IllegalArgumentException("Required parameter 'albumId' is null.");
    	}

    	// TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumMockData.getAlbum(albumId).getImageLink())).build();
    }

    @Override
    public Response browseAlbums(String pagingState, Integer items, String facets)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == items) {
    	    throw new IllegalArgumentException("Required parameter 'items' is null.");
    	}
    	
        AlbumList albumList = catalogService.browseAlbums(pagingState, items, facets, null).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", albumList)).build();
    }

    // ========================================================================================================== ARTIST
    // =================================================================================================================

    @Override
    public Response getArtist(String artistId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == artistId) {
    	    throw new IllegalArgumentException("Required parameter 'artistId' is null.");
    	}

    	ArtistInfo artistInfo = catalogService.getArtist(artistId, null).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistInfo)).build();
    }

    @Override
    public Response getArtistImage(String artistId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == artistId) {
    	    throw new IllegalArgumentException("Required parameter 'artistId' is null.");
    	}

        // TODO replace current mock implementation
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistMockData.getArtist(artistId).getImageLink())).build();
    }

    @Override
    public Response browseArtists(String pagingState, Integer items, String facets)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == items) {
    	    throw new IllegalArgumentException("Required parameter 'items' is null.");
    	}
    	
        ArtistList artistList = catalogService.browseArtists(pagingState, items, facets, null).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", artistList)).build();
    }

    // =========================================================================================================== SONGS
    // =================================================================================================================

    @Override
    public Response getSong(String songId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == songId) {
    	    throw new IllegalArgumentException("Required parameter 'songId' is null.");
    	}

        SongInfo songInfo = catalogService.getSong(songId, null).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songInfo)).build();
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
    	if (null == songId) {
    	    throw new IllegalArgumentException("Required parameter 'songId' is null.");
    	}

        // TODO replace current mock data
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songMockData.getSong(songId).getImageLink())).build();
    }

    @Override
    public Response browseSongs(String pagingState, Integer items, String facets)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == items) {
    	    throw new IllegalArgumentException("Required parameter 'items' is null.");
    	}

        SongList songList = catalogService.browseSongs(pagingState, items, facets, null).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", songList)).build();
    }

    @Override
    public Response playSong(String songId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == songId) {
    	    throw new IllegalArgumentException("Required parameter 'songId' is null.");
    	}

        // do some magic !
        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response commentSong(String songId, String sessionToken)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == songId) {
    	    throw new IllegalArgumentException("Required parameter 'songId' is null.");
    	}

        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response rateArtist(String artistId, Integer rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken.isEmpty()){
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response rateAlbum(String albumId, Integer rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken.isEmpty()){
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response rateSong(String songId, Integer rating, String sessionToken)
            throws NotFoundException {
        // TODO replace current mock data
        if (sessionToken.isEmpty()){
            return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.ERROR, "no sessionToken provided")).build();
        }
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
    	// Validate required parameters
    	if (null == email) {
    	    throw new IllegalArgumentException("Required parameter 'email' is null.");
    	}
    	if (null == password) {
    	    throw new IllegalArgumentException("Required parameter 'password' is null.");
    	}

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
    	// Validate required parameters
    	if (null == email) {
    	    throw new IllegalArgumentException("Required parameter 'email' is null.");
    	}

        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    // ----------------------------------------------------------------------------------------------------------- OTHER
    // =================================================================================================================

    @Override
    public Response getFacet(String facetId)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == facetId) {
    	    throw new IllegalArgumentException("Required parameter 'facetId' is null.");
    	}

        FacetInfoWithChildren facetInfoWithChildren = catalogService.getFacet(facetId).toBlocking().first();

        return Response.ok().entity(new MslApiResponseMessage(ApiResponseMessage.OK, "success", facetInfoWithChildren)).build();
    }


    @Override
    public Response searchFor(String searchText, String searchType)
            throws NotFoundException {
    	// Validate required parameters
    	if (null == searchText) {
    	    throw new IllegalArgumentException("Required parameter 'searchText' is null.");
    	}
    	if (null == searchType) {
    	    throw new IllegalArgumentException("Required parameter 'searchType' is null.");
    	}

        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response addSong(String songId, String sessionToken)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response addAlbum(String albumId, String sessionToken)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response addArtist(String artistId, String sessionToken)
            throws NotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

}
