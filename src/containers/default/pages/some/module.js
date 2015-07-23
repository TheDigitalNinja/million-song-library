import angular from "angular";
import defaultContainerSomePageConfig from "./config";

export default angular.module("msl.containers.default.some", [])
  .config(defaultContainerSomePageConfig)
  .name;
