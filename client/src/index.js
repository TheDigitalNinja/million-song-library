//Dependencies
import 'angular-material/angular-material.scss';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import 'slick-carousel/slick/fonts/slick.eot';
import 'slick-carousel/slick/fonts/slick.svg';
import 'slick-carousel/slick/fonts/slick.ttf';
import 'slick-carousel/slick/fonts/slick.woff';

import 'angular-toastr/dist/angular-toastr.css';

import angular from 'angular';
import uiRouter from 'angular-ui-router';
import angularAria from 'angular-aria';
import angularAnimate from 'angular-animate';
import angularMaterial from 'angular-material';
import slick from 'slick-carousel';
import toastr from 'angular-toastr';

//Layout
import layout from './layout/layout.module.js';

//Pages
import artistPage from './pages/artist/artist.module.js';
import albumPage from './pages/album/album.module.js';
import errorPage from './pages/error/error.module.js';
import homePage from './pages/home/home.module.js';
import loginPage from './pages/login/login.module.js';
import registrationPage from './pages/registration/registration.module.js';
import searchPage from './pages/search/search.module.js';
import songPage from './pages/song/song.module.js';
import userPage from './pages/user/user.module.js';
import libraryPage from './pages/library/library.module.js';

import starRating from './modules/star-rating/module.js';

//Modules
import authentication from 'modules/authentication/module';
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
  toastr,

  layout,
  artistPage,
  albumPage,
  errorPage,
  homePage,
  loginPage,
  registrationPage,
  searchPage,
  songPage,
  userPage,
  libraryPage,

  authentication,
  player,
  permission,

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
  .config((toastrConfig) => {
    angular.extend(toastrConfig, {
      autoDismiss: true,
      containerId: 'toast-container',
      timeOut: 5000,
      maxOpened: 3,
      newestOnTop: true,
      positionClass: 'toast-top-right',
      preventDuplicates: false,
      preventOpenDuplicates: false,
      target: 'body',
    });
  })
  .run(onRun)
  .name;
