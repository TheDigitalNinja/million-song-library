import angular from "angular";
import angularCookies from "angular-cookies";
import authorisation from "./authorisation";
import sessionToken from "./sessionToken";
import sessionTokenHttpInterceptor from "./sessionTokenHttpInterceptor";

export default angular.module("msl.authorisation", [angularCookies])
  .factory("authorisation", authorisation)
  .factory("sessionToken", sessionToken)
  .factory("sessionTokenHttpInterceptor", sessionTokenHttpInterceptor)
  .name;
