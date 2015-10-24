//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import timeFilter from './time-filter.js';

export default angular.module('msl.filters', [
  uiRouter,
])
  .filter('timeFilter', timeFilter)
  .name;
