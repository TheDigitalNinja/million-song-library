package io.swagger.model;

import java.util.*;
import java.math.BigDecimal;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-04T16:53:15.265-07:00")
public class AlbumInfo  {
  
  private String albumId = null;
  private String albumName = null;
  private String artistId = null;
  private String artistName = null;
  private String genre = null;
  private BigDecimal year = null;
  private BigDecimal averageRating = null;
  private BigDecimal personalRating = null;
  private String imageLink = null;
  private List<String> songsList = new ArrayList<String>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("album_id")
  public String getAlbumId() {
    return albumId;
  }
  public void setAlbumId(String albumId) {
    this.albumId = albumId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("album_name")
  public String getAlbumName() {
    return albumName;
  }
  public void setAlbumName(String albumName) {
    this.albumName = albumName;
  }

  
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
  @JsonProperty("genre")
  public String getGenre() {
    return genre;
  }
  public void setGenre(String genre) {
    this.genre = genre;
  }

  
  /**
   * The year the album was released
   **/
  @ApiModelProperty(value = "The year the album was released")
  @JsonProperty("year")
  public BigDecimal getYear() {
    return year;
  }
  public void setYear(BigDecimal year) {
    this.year = year;
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

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumInfo {\n");
    
    sb.append("  albumId: ").append(albumId).append("\n");
    sb.append("  albumName: ").append(albumName).append("\n");
    sb.append("  artistId: ").append(artistId).append("\n");
    sb.append("  artistName: ").append(artistName).append("\n");
    sb.append("  genre: ").append(genre).append("\n");
    sb.append("  year: ").append(year).append("\n");
    sb.append("  averageRating: ").append(averageRating).append("\n");
    sb.append("  personalRating: ").append(personalRating).append("\n");
    sb.append("  imageLink: ").append(imageLink).append("\n");
    sb.append("  songsList: ").append(songsList).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
