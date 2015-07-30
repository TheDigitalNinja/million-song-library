import "bootstrap/less/bootstrap.less";
import "font-awesome/less/font-awesome.less";
import angular from "angular";
import router from "angular-ui-router";
import permission from "./modules/permission/module";
import layouts from "./containers/module";
import bootstrap from "./modules/bootstrap/module";
import authorisation from "./modules/authorisation/module";
import defaultConfig from "./config";
import defaultRun from "./run";

export default angular.module("msl", [router, permission, layouts, bootstrap, authorisation])
  .config(defaultConfig)
  .run(defaultRun)
  .name;
