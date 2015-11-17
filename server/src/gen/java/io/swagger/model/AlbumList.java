package io.swagger.model;

import io.swagger.model.AlbumInfo;
import java.util.*;
import io.swagger.model.PagingState;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-13T16:33:04.991-06:00")
public class AlbumList  {
  
  private PagingState pagingState = null;
  private List<AlbumInfo> albums = new ArrayList<AlbumInfo>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("pagingState")
  public PagingState getPagingState() {
    return pagingState;
  }
  public void setPagingState(PagingState pagingState) {
    this.pagingState = pagingState;
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
    
    sb.append("  pagingState: ").append(pagingState).append("\n");
    sb.append("  albums: ").append(albums).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
