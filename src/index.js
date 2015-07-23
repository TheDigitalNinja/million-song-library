import "bootstrap/less/bootstrap.less";
import angular from "angular";
import routerModule from "angular-ui-router";
import defaultConfig from "./config";
import layoutsModule from "./containers/module";

var ngModule = angular.module("msl", [routerModule, layoutsModule])
  .config(defaultConfig);

export default ngModule;
