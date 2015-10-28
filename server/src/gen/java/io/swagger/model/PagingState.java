package io.swagger.model;


import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.JsonProperty;


@ApiModel(description = "")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-27T19:06:46.559-06:00")
public class PagingState  {
  
  private String pagingState = null;

  
  /**
   * PagingState indicates a position in a paginated query. It is sent from the server with each paginated result and returned to the server to retrieve the next page. A PagingState instance contains a GUID key to an EVCache entry on the server. The value of the EVCache entry contains the original Cassandra query and the PagingState returned after execution of the Cassandra paginated query.
   **/
  @ApiModelProperty(value = "PagingState indicates a position in a paginated query. It is sent from the server with each paginated result and returned to the server to retrieve the next page. A PagingState instance contains a GUID key to an EVCache entry on the server. The value of the EVCache entry contains the original Cassandra query and the PagingState returned after execution of the Cassandra paginated query.")
  @JsonProperty("pagingState")
  public String getPagingState() {
    return pagingState;
  }
  public void setPagingState(String pagingState) {
    this.pagingState = pagingState;
  }

  

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagingState {\n");
    
    sb.append("  pagingState: ").append(pagingState).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
