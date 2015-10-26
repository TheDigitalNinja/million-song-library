package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

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

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public abstract class ApiApiService {


    // ======================================================================================= SONGS

    public abstract Response getSong(String songId) throws NotFoundException;
    public abstract Response getSongById(String songId) throws NotFoundException;
    public abstract Response getRecentSongs() throws NotFoundException;
    public abstract Response getSongImage(String songId) throws NotFoundException;
    public abstract Response browseSongs(String pos, Integer items, String facets, String sortFields)
            throws NotFoundException;
    public abstract Response playSong(String songId) throws NotFoundException;
    public abstract Response commentSong(String songId) throws NotFoundException;
    public abstract Response rateSong(String songId, BigDecimal rating) throws NotFoundException;

    // ======================================================================================= ALBUMS

    public abstract Response getAlbum(String albumId) throws NotFoundException;
    public abstract Response getAlbums(String genreName) throws NotFoundException;
    public abstract Response getAlbumById (String albumId) throws NotFoundException;
    public abstract Response getAlbumImage(String albumId) throws NotFoundException;
    public abstract Response browseAlbums(String pos, Integer items, String facets, String sortFields)
            throws NotFoundException;

    // ======================================================================================= ARTISTS

    public abstract Response getArtist(String artistId) throws NotFoundException;
    public abstract Response getArtistList(String genreName) throws NotFoundException;
    public abstract Response getArtistAlbums (String artistId) throws NotFoundException;
    public abstract Response getSimilarArtists (String artistId) throws NotFoundException;
    public abstract Response getArtistImage(String artistId) throws NotFoundException;
    public abstract Response browseArtists(String pos, Integer items, String facets, String sortFields)
            throws NotFoundException;

    // ======================================================================================= LIBRARY

    public abstract Response songLibrary() throws NotFoundException;
    public abstract Response getMyLibrary() throws NotFoundException;
    public abstract Response addToLibrary(String songId) throws NotFoundException;

    // ======================================================================================= GENRE

    public abstract Response getGenreList() throws NotFoundException;
    public abstract Response getGenre(String genreName) throws NotFoundException;

    // ======================================================================================= SESSION

    public abstract Response getUserInfo() throws NotFoundException;
    public abstract Response login(String email, String password) throws NotFoundException;
    public abstract Response logout() throws NotFoundException;
    public abstract Response resetPassword(String email) throws NotFoundException;
    public abstract Response getSessionInfo() throws NotFoundException;

    // ---------------------------------------------------------------------------------------- OTHER

    public abstract Response getFacet(String facetId) throws NotFoundException;
    public abstract Response browseCatalog (String last, Integer items, String genre, String year, String artist, String album, Integer rating)
            throws NotFoundException;
    public abstract Response searchFor(String searchText, String searchType)
            throws NotFoundException;


}
