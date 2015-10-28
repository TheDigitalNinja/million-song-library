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

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-27T19:06:46.559-06:00")
public abstract class ApiApiService {
  
      public abstract Response getMyLibrary()
      throws NotFoundException;
  
      public abstract Response getUserInfo()
      throws NotFoundException;
  
      public abstract Response getRecentSongs()
      throws NotFoundException;
  
      public abstract Response getAlbum(String albumId)
      throws NotFoundException;
  
      public abstract Response getArtist(String artistId)
      throws NotFoundException;
  
      public abstract Response browseAlbums(String pagingState,Integer items,String facets,String sortFields)
      throws NotFoundException;
  
      public abstract Response browseArtists(String pagingState,Integer items,String facets,String sortFields)
      throws NotFoundException;
  
      public abstract Response browseSongs(String pagingState,Integer items,String facets,String sortFields)
      throws NotFoundException;
  
      public abstract Response getFacet(String facetId)
      throws NotFoundException;
  
      public abstract Response getSong(String songId)
      throws NotFoundException;
  
      public abstract Response getAlbumImage(String albumId)
      throws NotFoundException;
  
      public abstract Response getArtistImage(String artistId)
      throws NotFoundException;
  
      public abstract Response getSongImage(String songId)
      throws NotFoundException;
  
      public abstract Response login(String email,String password)
      throws NotFoundException;
  
      public abstract Response logout()
      throws NotFoundException;
  
      public abstract Response resetPassword(String email)
      throws NotFoundException;
  
      public abstract Response getSessionInfo()
      throws NotFoundException;
  
      public abstract Response playSong(String songId)
      throws NotFoundException;
  
      public abstract Response commentSong(String songId)
      throws NotFoundException;
  
      public abstract Response rateSong(String songId,BigDecimal rating)
      throws NotFoundException;
  
      public abstract Response searchFor(String searchText,String searchType)
      throws NotFoundException;
  
}
