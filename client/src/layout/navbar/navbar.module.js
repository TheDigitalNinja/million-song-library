//View
import '../../styles/layout.scss';

//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

//Modules
import authentication from 'modules/authentication/module';
import player from 'modules/player/module';

import navbarDirective from './navbar.directive.js';
import navbarCtrl from './navbar.controller.js';
import navbarSearchCtrl from './navbar-search.controller.js';

export default angular.module('msl.layout.navbar', [
  uiRouter,
  authentication,
  player,
])
  .directive('navbar', navbarDirective)
  .controller('navbarCtrl', navbarCtrl)
  .controller('navbarSearchCtrl', navbarSearchCtrl)
  .name;
