package io.swagger.model;

import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-20T17:56:37.516-06:00")
public class FacetInfoWithChildren  {
  
  private String facetId = null;
  private String name = null;
  public List<FacetInfoWithChildren> children = new ArrayList<FacetInfoWithChildren>();

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("facet_id")
  public String getFacetId() {
    return facetId;
  }
  public void setFacetId(String facetId) {
    this.facetId = facetId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("name")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("children")
  public List<FacetInfoWithChildren> getChildren() {
    return children;
  }
  public void setChildren(List<FacetInfoWithChildren> children) {
    this.children = children;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetInfoWithChildren {\n");
    
    sb.append("  facetId: ").append(facetId).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  children: ").append(children).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
