package io.swagger.model;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-19T15:56:09.144-06:00")
public class GenreInfo  {
  
  private String genreName = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("genre_name")
  public String getGenreName() {
    return genreName;
  }
  public void setGenreName(String genreName) {
    this.genreName = genreName;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenreInfo {\n");
    
    sb.append("  genreName: ").append(genreName).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
