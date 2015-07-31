import angular from "angular";
import defaultContainerHomePageConfig from "./config";
import homeCtrl from "./controllers/homeCtrl.js";

export default angular.module("msl.containers.default.home", [])
  .config(defaultContainerHomePageConfig)
  .controller("homeCtrl", homeCtrl)
  .name;
