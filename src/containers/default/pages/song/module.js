import "./stylesheets/default.less";
import angular from "angular";
import datastore from "modules/datastore/module";
import defaultContainerSongPageConfig from "./config";
import songCtrl from "./controllers/songCtrl.js";
import rating from "modules/star-rating/module";

export default angular.module("msl.containers.default.song", [rating, datastore])
  .config(defaultContainerSongPageConfig)
  .controller("songCtrl", songCtrl)
  .name;
