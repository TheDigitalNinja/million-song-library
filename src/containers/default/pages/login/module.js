import "./stylesheets/default.less";
import angular from "angular";
import authorisation from "authorisation/module";
import defaultContainerLoginPageConfig from "./config";
import loginCtrl from "./controllers/loginCtrl.js";

export default angular.module("msl.containers.default.login", [authorisation])
  .config(defaultContainerLoginPageConfig)
  .controller("loginCtrl", loginCtrl)
  .name;
