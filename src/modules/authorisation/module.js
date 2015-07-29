import angular from "angular";
import storage from "../storage/module";
import authorisation from "./authorisation";

export default angular.module("msl.authorisation", [storage])
  .factory("authorisation", authorisation)
  .name;
