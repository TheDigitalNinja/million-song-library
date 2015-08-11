import angular from "angular";
import request from "./request";
import entityMapper from "./entityMapper";
import myLibraryStore from "./services/myLibraryStore";

export default angular.module("msl.datastore", [])
  .factory("request", request)
  .factory("entityMapper", entityMapper)
  .factory("myLibraryStore", myLibraryStore)
  .name;
