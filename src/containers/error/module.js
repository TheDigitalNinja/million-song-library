import "./stylesheets/default.less";
import angular from "angular";
import errorContainerConfig from "./config";

export default angular.module("msl.containers.error", [])
  .config(errorContainerConfig)
  .name;
