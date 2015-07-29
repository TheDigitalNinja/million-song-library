import "./stylesheets/default.less";
import angular from "angular";
import defaultContainerArtistPageConfig from "./config";
import artistCtrl from "./controllers/artistCtrl.js";

export default angular.module("msl.containers.default.artist", [])
  .config(defaultContainerArtistPageConfig)
  .controller("artistCtrl", artistCtrl)
  .name;
