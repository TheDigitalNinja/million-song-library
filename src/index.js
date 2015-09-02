//Dependencies
import 'bootstrap/less/bootstrap.less';
import 'font-awesome/less/font-awesome.less';
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import bootstrap from './modules/bootstrap/module';

//Layout
import layout from './layout/layout.module.js';

//Pages
import artistPage from './pages/artist/artist.module.js';
import errorPage from './pages/error/error.module.js';
import homePage from './pages/home/home.module.js';
import loginPage from './pages/login/login.module.js';
import songPage from './pages/song/song.module.js';
import userPage from './pages/user/user.module.js';

//Modules
import authorisation from 'modules/authorisation/module';
import player from 'modules/player/module';
import permission from './modules/permission/module';
import routing from './routing.js';
import onRun from './run';

export default angular.module('msl', [
  uiRouter,

  layout,
  artistPage,
  errorPage,
  homePage,
  loginPage,
  songPage,
  userPage,

  authorisation,
  player,
  permission,
  bootstrap
])
  .config(routing)
  .run(onRun)
  .name;

