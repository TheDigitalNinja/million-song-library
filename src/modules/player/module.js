import "./stylesheets/default.less";
import angular from "angular";
import playerFactory from "./factories/player";
import playerDirective from "./directives/player";

export default angular.module("msl.player", [])
  .factory("player", playerFactory)
  .directive("player", playerDirective)
  .name;
