// Dependencies
import angular from 'angular';

import ratingFilter from './rating-filter/rating-filter.module.js';
import genreFilter from './genre-filter/genre-filter.module.js';
import filterModel from './filter.model.js';

export default angular.module('msl.layout.sideNav', [
  ratingFilter,
  genreFilter,
])
  .service('filterModel', filterModel)
  .name;
