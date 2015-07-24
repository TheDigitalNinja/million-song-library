import angular from "angular";
import defaultContainerHomePageConfig from "./config";

export default angular.module("msl.containers.default.home", [])
  .config(defaultContainerHomePageConfig)
  .name;
