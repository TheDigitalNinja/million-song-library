package io.swagger.model;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-27T19:06:46.559-06:00")
public class ErrorResponse  {
  
  private String message = null;

  
  /**
   **/
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    
    sb.append("  message: ").append(message).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
