package io.swagger.api.factories;

import io.swagger.api.ApiApiService;
import io.swagger.api.impl.ApiApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JaxRSServerCodegen", date = "2015-10-19T15:56:09.144-06:00")
public class ApiApiServiceFactory {

   private final static ApiApiService service = new ApiApiServiceImpl();

   public static ApiApiService getApiApi()
   {
      return service;
   }
}
