package io.swagger.model;

import java.math.BigDecimal;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-04T16:53:15.265-07:00")
public class SongInfo  {
  
  private String songId = null;
  private String songName = null;
  private String imageLink = null;
  private String artistId = null;
  private String artistName = null;
  private String albumId = null;
  private String albumName = null;
  private Integer duration = null;
  private String genre = null;
  private BigDecimal danceability = null;
  private BigDecimal averageRating = null;
  private BigDecimal personalRating = null;
  private BigDecimal songHotttnesss = null;
  private BigDecimal year = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("song_id")
  public String getSongId() {
    return songId;
  }
  public void setSongId(String songId) {
    this.songId = songId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("song_name")
  public String getSongName() {
    return songName;
  }
  public void setSongName(String songName) {
    this.songName = songName;
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
   * Length of song in seconds
   **/
  @ApiModelProperty(value = "Length of song in seconds")
  @JsonProperty("duration")
  public Integer getDuration() {
    return duration;
  }
  public void setDuration(Integer duration) {
    this.duration = duration;
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
  @JsonProperty("danceability")
  public BigDecimal getDanceability() {
    return danceability;
  }
  public void setDanceability(BigDecimal danceability) {
    this.danceability = danceability;
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
   * The Echo Nest hotttnesss score
   **/
  @ApiModelProperty(value = "The Echo Nest hotttnesss score")
  @JsonProperty("song_hotttnesss")
  public BigDecimal getSongHotttnesss() {
    return songHotttnesss;
  }
  public void setSongHotttnesss(BigDecimal songHotttnesss) {
    this.songHotttnesss = songHotttnesss;
  }

  
  /**
   * The year the song was released
   **/
  @ApiModelProperty(value = "The year the song was released")
  @JsonProperty("year")
  public BigDecimal getYear() {
    return year;
  }
  public void setYear(BigDecimal year) {
    this.year = year;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SongInfo {\n");
    
    sb.append("  songId: ").append(songId).append("\n");
    sb.append("  songName: ").append(songName).append("\n");
    sb.append("  imageLink: ").append(imageLink).append("\n");
    sb.append("  artistId: ").append(artistId).append("\n");
    sb.append("  artistName: ").append(artistName).append("\n");
    sb.append("  albumId: ").append(albumId).append("\n");
    sb.append("  albumName: ").append(albumName).append("\n");
    sb.append("  duration: ").append(duration).append("\n");
    sb.append("  genre: ").append(genre).append("\n");
    sb.append("  danceability: ").append(danceability).append("\n");
    sb.append("  averageRating: ").append(averageRating).append("\n");
    sb.append("  personalRating: ").append(personalRating).append("\n");
    sb.append("  songHotttnesss: ").append(songHotttnesss).append("\n");
    sb.append("  year: ").append(year).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}