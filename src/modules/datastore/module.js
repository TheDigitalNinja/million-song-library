import angular from "angular";
import request from "./request";
import entityMapper from "./entityMapper";
import myLibraryStore from "./services/myLibraryStore";
import songStore from "./services/songStore";

export default angular.module("msl.datastore", [])
  .factory("request", request)
  .factory("entityMapper", entityMapper)
  .factory("myLibraryStore", myLibraryStore)
  .factory("songStore", songStore)
  .name;