import angular from 'angular';
import songsTable from './directives/songs-table';

export default angular.module('msl.songsTable', [])
  .directive('songsTable', songsTable)
  .name;
