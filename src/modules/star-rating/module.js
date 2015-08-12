import "./stylesheets/default.less";
import angular from "angular";
import starRating from "./directives/star-rating";

export default angular.module("msl.rating", [])
  .directive("starRating", starRating)
  .name;
