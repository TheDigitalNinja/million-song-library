package io.swagger.model;

import io.swagger.model.AlbumList;
import io.swagger.model.SongList;
import io.swagger.model.ArtistList;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-04T16:53:15.265-07:00")
public class MyLibrary  {
  
  private AlbumList albums = null;
  private ArtistList artists = null;
  private SongList songs = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("albums")
  public AlbumList getAlbums() {
    return albums;
  }
  public void setAlbums(AlbumList albums) {
    this.albums = albums;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("artists")
  public ArtistList getArtists() {
    return artists;
  }
  public void setArtists(ArtistList artists) {
    this.artists = artists;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("songs")
  public SongList getSongs() {
    return songs;
  }
  public void setSongs(SongList songs) {
    this.songs = songs;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class MyLibrary {\n");
    
    sb.append("  albums: ").append(albums).append("\n");
    sb.append("  artists: ").append(artists).append("\n");
    sb.append("  songs: ").append(songs).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}