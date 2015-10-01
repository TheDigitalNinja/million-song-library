//View
import './stylesheets/song.less';

//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';
import rating from 'modules/star-rating/module';

import songPageRoute from './song.route.js';
import songsList from './directives/songs-list.js';
import songsListCtrl from './controllers/songs-list.controller.js';
import songCtrl from './controllers/song.controller.js';

export default angular.module('msl.song', [
  router,
  rating,
  dataStore,
])
  .config(songPageRoute)
  .directive('songsList', songsList)
  .controller('songsListCtrl', songsListCtrl)
  .controller('songCtrl', songCtrl)
  .name;
