//Dependencies
import 'bootstrap/less/bootstrap.less';
import 'font-awesome/less/font-awesome.less';
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import uiBootstrap from 'angular-ui-bootstrap';
import bootstrap from './modules/bootstrap/module';

//Layout
import layout from './layout/layout.module.js';

//Pages
import artistPage from './pages/artist/artist.module.js';
import albumPage from './pages/album/album.module.js';
import errorPage from './pages/error/error.module.js';
import homePage from './pages/home/home.module.js';
import loginPage from './pages/login/login.module.js';
import searchPage from './pages/search/search.module.js';
import songPage from './pages/song/song.module.js';
import userPage from './pages/user/user.module.js';
import browsePage from './pages/browse/browse.module.js';
import libraryPage from './pages/library/library.module.js';

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
  albumPage,
  errorPage,
  homePage,
  loginPage,
  searchPage,
  songPage,
  userPage,
  browsePage,
  libraryPage,

  authorisation,
  player,
  permission,
  bootstrap,
  uiBootstrap,
])
  .config(routing)
  .run(onRun)
  .name;
