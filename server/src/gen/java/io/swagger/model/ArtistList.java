package io.swagger.model;

import java.util.*;
import io.swagger.model.ArtistInfo;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class ArtistList  {
  
  private String lastPos = null;
  private List<ArtistInfo> artists = new ArrayList<ArtistInfo>();

  
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
  @JsonProperty("artists")
  public List<ArtistInfo> getArtists() {
    return artists;
  }
  public void setArtists(List<ArtistInfo> artists) {
    this.artists = artists;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ArtistList {\n");
    
    sb.append("  lastPos: ").append(lastPos).append("\n");
    sb.append("  artists: ").append(artists).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
