import angular from "angular";
import router from "angular-ui-router";
import defaultContainerMyPageConfig from "./config";

export default angular.module("msl.containers.default.my", [router])
  .config(defaultContainerMyPageConfig)
  .name;
