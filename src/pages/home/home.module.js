//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

import homeRoute from './home.route.js';
import homeCtrl from './controllers/home.controller.js';

export default angular.module('msl.home', [uiRouter])
  .config(homeRoute)
  .controller('homeCtrl', homeCtrl)
  .name;
