import "./stylesheets/default.less";
import angular from "angular";
import homePageModule from "./pages/home/module";
import somePageModule from "./pages/some/module";
import defaultContainerConfig from "./config";
import headerCtrl from "./controllers/headerCtrl";

export default angular.module("msl.containers.default", [homePageModule, somePageModule])
  .config(defaultContainerConfig)
  .controller("headerCtrl", headerCtrl)
  .name;
