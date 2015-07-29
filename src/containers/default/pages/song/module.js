import "./stylesheets/default.less";
import angular from "angular";
import defaultContainerSongPageConfig from "./config";
import songCtrl from "./controllers/songCtrl.js";

export default angular.module("msl.containers.default.song", [])
  .config(defaultContainerSongPageConfig)
  .controller("songCtrl", songCtrl)
  .name;
