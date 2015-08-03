import "./stylesheets/default.less";
import angular from "angular";
import router from "angular-ui-router";
import defaultContainerSearchPageConfig from "./config";
import searchCtrl from "./controllers/searchCtrl.js";

export default angular.module("msl.containers.default.search", [router])
  .config(defaultContainerSearchPageConfig)
  .controller("searchCtrl", searchCtrl)
  .name;
