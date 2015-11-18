//Dependencies
import 'font-awesome/scss/font-awesome.scss';
import 'angular-material/angular-material.scss';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/fonts/slick.eot';
import 'slick-carousel/slick/fonts/slick.svg';
import 'slick-carousel/slick/fonts/slick.ttf';
import 'slick-carousel/slick/fonts/slick.woff';
import angular from 'angular';
import uiRouter from 'angular-ui-router';
import angularAria from 'angular-aria';
import angularAnimate from 'angular-animate';
import angularMaterial from 'angular-material';
import bootstrap from './modules/bootstrap/module';
import slick from 'slick-carousel';

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
import libraryPage from './pages/library/library.module.js';

import starRating from './modules/star-rating/module.js';

//Modules
import authorisation from 'modules/authorisation/module';
import player from 'modules/player/module';
import permission from './modules/permission/module';
import routing from './routing.js';
import onRun from './run';
import filters from './filters/filters.module.js';

export default angular.module('msl', [
  uiRouter,
  angularAria,
  angularAnimate,
  angularMaterial,

  layout,
  artistPage,
  albumPage,
  errorPage,
  homePage,
  loginPage,
  searchPage,
  songPage,
  userPage,
  libraryPage,

  authorisation,
  player,
  permission,
  bootstrap,

  starRating,
  filters,
])
  .config(routing)
  .config(
    ($mdThemingProvider) => {
      $mdThemingProvider.theme('default')
      .primaryPalette('blue')
      .accentPalette('deep-orange');
    }
  )
  .run(onRun)
  .name;
