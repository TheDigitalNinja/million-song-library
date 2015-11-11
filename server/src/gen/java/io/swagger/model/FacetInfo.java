package io.swagger.model;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-11-12T17:31:41.997-06:00")
public class FacetInfo  {

  private String facetId = null;
  private String name = null;


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



  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class FacetInfo {\n");

    sb.append("  facetId: ").append(facetId).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
