//View
import './stylesheets/login.scss';
//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';
//Module
import authorisation from 'modules/authorisation/module';

import loginRoute from './login.route.js';
import loginCtrl from './controllers/login.controller.js';

export default angular.module('msl.login', [
  uiRouter,
    authorisation,
  ])
  .config(loginRoute)
  .controller('loginCtrl', loginCtrl)
  .name;
