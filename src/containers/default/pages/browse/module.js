import "./stylesheets/default.less";
import angular from "angular";
import defaultContainerBrowsePageConfig from "./config";
import browseCtrl from "./controllers/browseCtrl.js";

export default angular.module("msl.containers.default.browse", [])
  .config(defaultContainerBrowsePageConfig)
  .controller("browseCtrl", browseCtrl)
  .name;
