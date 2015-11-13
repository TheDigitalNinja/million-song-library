package io.swagger.model;

import java.math.BigDecimal;
import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-10T16:56:12.664-06:00")
public class ArtistInfo  {
  
  private String artistId = null;
  private String artistName = null;
  private List<String> albumsList = new ArrayList<String>();
  private BigDecimal averageRating = null;
  private BigDecimal personalRating = null;
  private String imageLink = null;
  private List<String> songsList = new ArrayList<String>();
  private String genre = null;
  private List<String> similarArtistsList = new ArrayList<String>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("artist_id")
  public String getArtistId() {
    return artistId;
  }
  public void setArtistId(String artistId) {
    this.artistId = artistId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("artist_name")
  public String getArtistName() {
    return artistName;
  }
  public void setArtistName(String artistName) {
    this.artistName = artistName;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("albums_list")
  public List<String> getAlbumsList() {
    return albumsList;
  }
  public void setAlbumsList(List<String> albumsList) {
    this.albumsList = albumsList;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("average_rating")
  public BigDecimal getAverageRating() {
    return averageRating;
  }
  public void setAverageRating(BigDecimal averageRating) {
    this.averageRating = averageRating;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("personal_rating")
  public BigDecimal getPersonalRating() {
    return personalRating;
  }
  public void setPersonalRating(BigDecimal personalRating) {
    this.personalRating = personalRating;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("image_link")
  public String getImageLink() {
    return imageLink;
  }
  public void setImageLink(String imageLink) {
    this.imageLink = imageLink;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("songs_list")
  public List<String> getSongsList() {
    return songsList;
  }
  public void setSongsList(List<String> songsList) {
    this.songsList = songsList;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("genre")
  public String getGenre() {
    return genre;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("similar_artists_list")
  public List<String> getSimilarArtistsList() {
    return similarArtistsList;
  }
  public void setSimilarArtistsList(List<String> similarArtistsList) {
    this.similarArtistsList = similarArtistsList;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArtistInfo {\n");
    
    sb.append("  artistId: ").append(artistId).append("\n");
    sb.append("  artistName: ").append(artistName).append("\n");
    sb.append("  albumsList: ").append(albumsList).append("\n");
    sb.append("  averageRating: ").append(averageRating).append("\n");
    sb.append("  personalRating: ").append(personalRating).append("\n");
    sb.append("  imageLink: ").append(imageLink).append("\n");
    sb.append("  songsList: ").append(songsList).append("\n");
    sb.append("  genre: ").append(genre).append("\n");
    sb.append("  similarArtistsList: ").append(similarArtistsList).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
