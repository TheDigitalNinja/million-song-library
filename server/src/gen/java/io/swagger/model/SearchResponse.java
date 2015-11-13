package io.swagger.model;

import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-13T16:33:04.991-06:00")
public class SearchResponse  {
  
  private List<String> searchResults = new ArrayList<String>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("search_results")
  public List<String> getSearchResults() {
    return searchResults;
  }
  public void setSearchResults(List<String> searchResults) {
    this.searchResults = searchResults;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResponse {\n");
    
    sb.append("  searchResults: ").append(searchResults).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
