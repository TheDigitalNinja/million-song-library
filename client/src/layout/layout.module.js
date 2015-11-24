//Styles
import '../styles/layout.scss';
//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

//Modules
import authentication from 'modules/authentication/module';
import player from 'modules/player/module';

import sideNavModule from './sidenav/sidenav.module.js';
import navbarModule from './navbar/navbar.module.js';
import layoutRoute from './layout.route.js';

export default angular.module('msl.layout', [
  uiRouter,
  authentication,
  player,
  navbarModule,
  sideNavModule,
])
  .config(layoutRoute)
  .name;
