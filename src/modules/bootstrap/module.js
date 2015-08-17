import angular from "angular";
import dropdown from "./dropdown";

/**
 * angular bootstrap module for using native bootstrap functions
 * in an angular way
 */
export default angular.module("msl.bootstrap", [])
  .directive("dropdown", dropdown)
  .name;
