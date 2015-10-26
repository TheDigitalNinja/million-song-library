package io.swagger.model;

import io.swagger.model.AlbumInfo;
import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class AlbumList  {
  
  private String lastPos = null;
  private List<AlbumInfo> albums = new ArrayList<AlbumInfo>();

  
  /**
   * The key to pass as the "pos" parameter when retrieving the next page
   **/
  @ApiModelProperty(value = "The key to pass as the \"pos\" parameter when retrieving the next page")
  @JsonProperty("last_pos")
  public String getLastPos() {
    return lastPos;
  }
  public void setLastPos(String lastPos) {
    this.lastPos = lastPos;
  }

  
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
    sb.append("class AlbumList {\n");
    
    sb.append("  lastPos: ").append(lastPos).append("\n");
    sb.append("  albums: ").append(albums).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
