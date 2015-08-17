import "./stylesheets/default.less";
import angular from "angular";
import errorContainerConfig from "./config";

/**
 * default error page module
 * all pages related with errors are handled in this module
 */
export default angular.module("msl.containers.error", [])
  .config(errorContainerConfig)
  .name;
