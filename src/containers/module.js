import angular from "angular";
import defaultContainer from "./default/module";
import errorContainer from "./error/module";

export default angular.module("msl.containers", [defaultContainer, errorContainer])
  .name;
