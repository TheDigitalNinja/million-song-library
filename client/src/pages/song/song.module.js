//View
import './stylesheets/song.scss';

//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';
import rating from 'modules/star-rating/module';
import authentication from 'modules/authentication/module';

import songPageRoute from './song.route.js';
import songCtrl from './controllers/song.controller.js';
import songBoxCtrl from './controllers/song-box.controller.js';

import songsList from './directives/songs-list.js';

import songBox from './directives/song-box.js';

import songModel from './models/song.model.js';

export default angular.module('msl.song', [
  router,
  rating,
  dataStore,
  authentication,
])
  .config(songPageRoute)
  .controller('songCtrl', songCtrl)
  .controller('songBoxCtrl', songBoxCtrl)
  .directive('songBox', songBox)
  .directive('songsList', songsList)
  .factory('songModel', songModel)
  .name;
