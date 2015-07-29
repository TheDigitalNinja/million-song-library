import angular from "angular";
import dropdown from "./dropdown";

export default angular.module("msl.bootstrap", [])
  .directive("dropdown", dropdown)
  .name;
