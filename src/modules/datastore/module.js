import angular from "angular";
import request from "./request";
import entityMapper from "./entityMapper";
import myLibraryStore from "./services/myLibraryStore";
import recentSongsStore from "./services/recentSongsStore";
import songStore from "./services/songStore";
import sessionInfoStore from "./services/sessionInfoStore";
import artistStore from "./services/artistStore";
import catalogStore from "./services/catalogStore";
import loginStore from "./services/loginStore";
import logoutStore from "./services/logoutStore";

export default angular.module("msl.datastore", [])
  .factory("request", request)
  .factory("entityMapper", entityMapper)
  .factory("myLibraryStore", myLibraryStore)
  .factory("recentSongsStore", recentSongsStore)
  .factory("songStore", songStore)
  .factory("sessionInfoStore", sessionInfoStore)
  .factory("artistStore", artistStore)
  .factory("catalogStore", catalogStore)
  .factory("loginStore", loginStore)
  .factory("logoutStore", logoutStore)
  .name;
