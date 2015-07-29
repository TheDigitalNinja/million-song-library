import angular from "angular";
import router from "angular-ui-router";
import defaultContainerPersonalContentPageConfig from "./config";

export default angular.module("msl.containers.default.personal-content", [router])
  .config(defaultContainerPersonalContentPageConfig)
  .name;
