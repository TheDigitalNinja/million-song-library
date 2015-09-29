//Dependencies
import angular from 'angular';

import artistFilter from './artist_filter.directive.js';
export default angular.module('msl.layout.sideNav.artistFilter', [])
  .directive('artistFilter', artistFilter.directiveFactory)
  .name;
