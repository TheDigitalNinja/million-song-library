import "./stylesheets/default.less";
import angular from "angular";
import authorisationModule from "../../authorisation/module";
import homePageModule from "./pages/home/module";
import somePageModule from "./pages/some/module";
import loginPageModule from "./pages/login/module";
import defaultContainerConfig from "./config";
import headerCtrl from "./controllers/headerCtrl";

export default angular.module("msl.containers.default", [
  authorisationModule,
  homePageModule,
  somePageModule,
  loginPageModule
])
  .config(defaultContainerConfig)
  .controller("headerCtrl", headerCtrl)
  .name;
