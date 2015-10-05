//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import libraryRoute from './library.route.js';
import libraryCtrl from './controllers/library.controller.js';
import dataStore from 'modules/datastore/module';
import libraryCarouselDirective from './library-carousel.directive.js';

export default angular.module('msl.library', [
    uiRouter,
    dataStore,
    ])
  .config(libraryRoute)
  .controller('libraryCtrl', libraryCtrl)
  .directive('libraryCarousel', libraryCarouselDirective)
  .name;
