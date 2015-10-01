//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import homeRoute from './home.route.js';
import homeCtrl from './controllers/home.controller.js';
import songsList from './directives/songs-list.js';
import albumsList from './directives/albums-list.js';
import artistsList from './directives/artists-list.js';
import dataStore from 'modules/datastore/module';

export default angular.module('msl.home', [
  uiRouter,
  dataStore,
])
  .config(homeRoute)
  .directive('songsList', songsList)
  .directive('albumsList', albumsList)
  .directive('artistsList', artistsList)
  .controller('homeCtrl', homeCtrl)
  .name;
