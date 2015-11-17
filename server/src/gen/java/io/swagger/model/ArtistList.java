package io.swagger.model;

import java.util.*;
import io.swagger.model.PagingState;
import io.swagger.model.ArtistInfo;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-13T16:33:04.991-06:00")
public class ArtistList  {
  
  private PagingState pagingState = null;
  private List<ArtistInfo> artists = new ArrayList<ArtistInfo>();

  
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
    
    sb.append("  pagingState: ").append(pagingState).append("\n");
    sb.append("  artists: ").append(artists).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
