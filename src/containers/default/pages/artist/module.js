import "./stylesheets/default.less";
import angular from "angular";
import datastore from "modules/datastore/module";
import defaultContainerArtistPageConfig from "./config";
import artistCtrl from "./controllers/artistCtrl.js";

export default angular.module("msl.containers.default.artist", [datastore])
  .config(defaultContainerArtistPageConfig)
  .controller("artistCtrl", artistCtrl)
  .name;
