// Dependencies
import angular from 'angular';

import genreFilter from './genre-filter.directive.js';

export default angular.module('msl.layout.sideNav.genreFilter', [])
  .directive('genreFilter', genreFilter.directiveFactory)
  .name;
