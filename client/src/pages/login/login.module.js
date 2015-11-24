//View
import './stylesheets/login.scss';
//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';
//Module
import authentication from 'modules/authentication/module';

import loginRoute from './login.route.js';
import loginCtrl from './controllers/login.controller.js';

export default angular.module('msl.login', [
  uiRouter,
    authentication,
  ])
  .config(loginRoute)
  .controller('loginCtrl', loginCtrl)
  .name;
