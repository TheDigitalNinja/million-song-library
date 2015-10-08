//View
import './stylesheets/song.scss';

//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';
import rating from 'modules/star-rating/module';

import songPageRoute from './song.route.js';
import songCtrl from './controllers/song.controller.js';

import songsList from './directives/songs-list.js';

import songBox from './directives/song-box.js';
import songBoxCtrl from './controllers/song-box.controller.js';

export default angular.module('msl.song', [
  router,
  rating,
  dataStore,
])
  .config(songPageRoute)
  .controller('songCtrl', songCtrl)
  .directive('songBox', songBox)
  .directive('songsList', songsList)
  .controller('songBoxCtrl', songBoxCtrl)
  .name;
