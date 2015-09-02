//Styles
import '../styles/layout.less';
//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

//Modules
import authorisation from 'modules/authorisation/module';
import player from 'modules/player/module';

import navbarModule from './navbar/navbar.module.js';
import layoutRoute from './layout.route.js';

export default angular.module('msl.layout', [
  uiRouter,
  authorisation,
  player,
  navbarModule
])
  .config(layoutRoute)
  .name;
