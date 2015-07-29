import angular from "angular";
import cookies from "angular-cookies";
import storage from "./storage";

export default angular.module("msl.storage", [cookies])
  .factory("storage", storage)
  .name;
