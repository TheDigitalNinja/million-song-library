package io.swagger.model;

import java.util.*;
import io.swagger.model.SongInfo;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class SongList  {
  
  private String lastPos = null;
  private List<SongInfo> songs = new ArrayList<SongInfo>();

  
  /**
   * The key to pass as the "pos" parameter when retrieving the next page.
   **/
  @ApiModelProperty(value = "The key to pass as the \"pos\" parameter when retrieving the next page.")
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
    
    sb.append("  lastPos: ").append(lastPos).append("\n");
    sb.append("  songs: ").append(songs).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
