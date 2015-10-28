package io.swagger.model;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-27T19:06:46.559-06:00")
public class SessionInfo  {
  
  private String userId = null;
  private String userEmail = null;

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }

  
  /**
   **/
  @ApiModelProperty(value = "")
  @JsonProperty("userEmail")
  public String getUserEmail() {
    return userEmail;
  }
  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SessionInfo {\n");
    
    sb.append("  userId: ").append(userId).append("\n");
    sb.append("  userEmail: ").append(userEmail).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
