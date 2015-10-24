package io.swagger.model;

import io.swagger.model.AlbumInfo;
import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-19T15:56:09.144-06:00")
public class AlbumsList  {
  
  private List<AlbumInfo> albums = new ArrayList<AlbumInfo>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("albums")
  public List<AlbumInfo> getAlbums() {
    return albums;
  }
  public void setAlbums(List<AlbumInfo> albums) {
    this.albums = albums;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AlbumsList {\n");
    
    sb.append("  albums: ").append(albums).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
