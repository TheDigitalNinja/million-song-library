import angular from "angular";
import songsTable from "./directives/songsTable";

export default angular.module("msl.songsTable", [])
  .directive("songsTable", songsTable)
  .name;
