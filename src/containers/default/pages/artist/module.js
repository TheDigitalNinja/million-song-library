import "./stylesheets/default.less";
import angular from "angular";
import datastore from "modules/datastore/module";
import defaultContainerArtistPageConfig from "./config";
import artistCtrl from "./controllers/artistCtrl.js";
import songsTable from "modules/songs-table/module";

export default angular.module("msl.containers.default.artist", [
  datastore,
  songsTable
])
  .config(defaultContainerArtistPageConfig)
  .controller("artistCtrl", artistCtrl)
  .name;
