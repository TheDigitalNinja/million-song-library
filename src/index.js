import "bootstrap/less/bootstrap.less";
import "font-awesome/less/font-awesome.less";
import angular from "angular";
import router from "angular-ui-router";
import layouts from "./containers/module";
import bootstrap from "./modules/bootstrap/module";
import defaultConfig from "./config";

export default angular.module("msl", [router, layouts, bootstrap])
  .config(defaultConfig)
  .name;
