//Dependencies
import angular from 'angular';

import ratingFilter from './rating-filter.directive.js';

export default angular.module('msl.layout.sideNav.ratingFilter', [])
  .directive('ratingFilter', ratingFilter.directiveFactory)
  .name;
