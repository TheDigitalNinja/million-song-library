package io.swagger.model;

import io.swagger.model.GenreInfo;
import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-19T15:56:09.144-06:00")
public class GenreList  {
  
  private String genre = null;
  private List<GenreInfo> genres = new ArrayList<GenreInfo>();

  
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
  @JsonProperty("genres")
  public List<GenreInfo> getGenres() {
    return genres;
  }
  public void setGenres(List<GenreInfo> genres) {
    this.genres = genres;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenreList {\n");
    
    sb.append("  genre: ").append(genre).append("\n");
    sb.append("  genres: ").append(genres).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
