import "./stylesheets/default.less";
import angular from "angular";
import defaultContainerLoginPageConfig from "./config";
import loginCtrl from "./controllers/loginCtrl.js";

export default angular.module("msl.containers.default.login", [])
  .config(defaultContainerLoginPageConfig)
  .controller("loginCtrl", loginCtrl)
  .name;
