package io.swagger.model;

import io.swagger.model.FacetInfo;
import java.util.*;

import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-04T16:53:15.265-07:00")
public class FacetInfoWithChildren  {
  
  private String facetId = null;
  private String name = null;
  private List<FacetInfo> children = new ArrayList<FacetInfo>();

  
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
  public List<FacetInfo> getChildren() {
    return children;
  }
  public void setChildren(List<FacetInfo> children) {
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