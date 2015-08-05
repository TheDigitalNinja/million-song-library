import angular from "angular";
import storage from "../storage/module";
import authorisation from "./authorisation";
import sessionToken from "./sessionToken";

export default angular.module("msl.authorisation", [storage])
  .factory("authorisation", authorisation)
  .factory("sessionToken", sessionToken)
  .name;
