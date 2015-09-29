// Dependencies
import angular from 'angular';

import ratingFilter from './rating_filter/rating_filter.module.js';
import genreFilter from './genre_filter/genre_filter.module.js';
import artistFilter from './artist_filter/artist_filter.module.js';

export default angular.module('msl.layout.sideNav', [
  ratingFilter,
  genreFilter,
  artistFilter,
])
  .name;
