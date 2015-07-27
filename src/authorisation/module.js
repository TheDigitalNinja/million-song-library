import angular from "angular";
import authorisation from "./authorisation";

export default angular.module("msl.authorisation", [])
  .factory("authorisation", authorisation)
  .name;
