//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import homeRoute from './home.route.js';
import homeCtrl from './controllers/home.controller.js';
import dataStore from 'modules/datastore/module';

export default angular.module('msl.home', [
  uiRouter,
  dataStore,
])
  .config(homeRoute)
  .controller('homeCtrl', homeCtrl)
  .name;
