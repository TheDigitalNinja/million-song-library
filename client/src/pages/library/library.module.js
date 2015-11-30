//Dependencies
import $ from 'jquery';
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import libraryRoute from './library.route.js';
import libraryCtrl from './controllers/library.controller.js';
import libraryModel from './models/library.model.js';
import addToLibrary from './directives/add-to-library.js';
import removeFromLibrary from './directives/remove-from-library.js';
import dataStore from 'modules/datastore/module';
import libraryCarouselDirective from './library-carousel.directive.js';
import repeatComplete from './directives/repeat-complete.js';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/fonts/slick.eot';
import 'slick-carousel/slick/fonts/slick.svg';
import 'slick-carousel/slick/fonts/slick.ttf';
import 'slick-carousel/slick/fonts/slick.woff';
import slickCarousel from 'slick-carousel';

export default angular.module('msl.library', [
    uiRouter,
    dataStore,
    ])
  .config(libraryRoute)
  .controller('libraryCtrl', libraryCtrl)
  .directive('libraryCarousel', libraryCarouselDirective)
  .directive('addToLibrary', addToLibrary)
  .directive('removeFromLibrary', removeFromLibrary)
  .directive('repeatComplete', repeatComplete)
  .service('libraryModel', libraryModel)
  .name;
