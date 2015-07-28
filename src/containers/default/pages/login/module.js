import "./stylesheets/default.less";
import angular from "angular";
import router from "angular-ui-router";
import authorisation from "authorisation/module";
import defaultContainerLoginPageConfig from "./config";
import loginCtrl from "./controllers/loginCtrl.js";

export default angular.module("msl.containers.default.login", [router, authorisation])
  .config(defaultContainerLoginPageConfig)
  .controller("loginCtrl", loginCtrl)
  .name;
