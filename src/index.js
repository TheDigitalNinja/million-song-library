import "bootstrap/less/bootstrap.less";
import "font-awesome/less/font-awesome.less";
import angular from "angular";
import router from "angular-ui-router";
import layouts from "./containers/module";
import defaultConfig from "./config";

export default angular.module("msl", [router, layouts])
  .config(defaultConfig)
  .name;
