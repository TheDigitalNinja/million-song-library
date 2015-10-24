import angular from 'angular';
import songsTable from './directives/songsTable';
import songsTableCtrl from './controllers/songs-table.controller.js';

export default angular.module('msl.songsTable', [])
  .directive('songsTable', songsTable)
  .controller('songsTableCtrl', songsTableCtrl)
  .name;
