package io.swagger.model;

import java.util.*;
import io.swagger.model.PagingState;
import io.swagger.model.SongInfo;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-10T16:56:12.664-06:00")
public class SongList  {
  
  private PagingState pagingState = null;
  private List<SongInfo> songs = new ArrayList<SongInfo>();

  
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
  @JsonProperty("songs")
  public List<SongInfo> getSongs() {
    return songs;
  }
  public void setSongs(List<SongInfo> songs) {
    this.songs = songs;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SongList {\n");
    
    sb.append("  pagingState: ").append(pagingState).append("\n");
    sb.append("  songs: ").append(songs).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
