import angular from "angular";
import request from "./request";
import entityMapper from "./entityMapper";
import myLibraryStore from "./services/myLibraryStore";
import recentSongsStore from "./services/recentSongsStore";
import songStore from "./services/songStore";

export default angular.module("msl.datastore", [])
  .factory("request", request)
  .factory("entityMapper", entityMapper)
  .factory("myLibraryStore", myLibraryStore)
  .factory("recentSongsStore", recentSongsStore)
  .factory("songStore", songStore)
  .name;
