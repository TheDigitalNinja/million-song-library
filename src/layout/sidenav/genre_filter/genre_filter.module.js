// Dependencies
import angular from 'angular';

import genreFilterModel from './genre_filter.model.js';
import genreFilter from './genre_filter.directive.js';

export default angular.module('msl.layout.sideNav.genreFilter', [])
  .factory('genreFilterModel', genreFilterModel)
  .directive('genreFilter', genreFilter.directiveFactory)
  .name;
