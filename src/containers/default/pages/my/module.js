import angular from "angular";
import router from "angular-ui-router";
import datastore from "modules/datastore/module";
import defaultContainerMyPageConfig from "./config";
import historyCtrl from "./controllers/historyCtrl";
import libraryCtrl from "./controllers/libraryCtrl";
import likesCtrl from "./controllers/likesCtrl";

export default angular.module("msl.containers.default.my", [router, datastore])
  .controller("historyCtrl", historyCtrl)
  .controller("libraryCtrl", libraryCtrl)
  .controller("likesCtrl", likesCtrl)
  .config(defaultContainerMyPageConfig)
  .name;
