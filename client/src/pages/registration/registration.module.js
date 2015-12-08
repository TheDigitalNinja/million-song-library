import './stylesheets/registration.scss';
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import ngMessages from 'angular-messages';
import registrationRoute from './registration.route.js';
import matchDirective from './directives/match.js';
import registrationCtrl from './controllers/registration.controller.js';
import dataStore from 'modules/datastore/module';

export default angular.module('msl.registration', [
  uiRouter,
  dataStore,
  ngMessages,
])
  .config(registrationRoute)
  .directive('match', matchDirective)
  .controller('registrationCtrl', registrationCtrl)
  .name;
