//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';

import albumPageRoute from './album.route.js';
import albumCtrl from './controllers/album.controller.js';

import albumsList from './directives/albums-list.js';

import albumBox from './directives/album-box.js';
import albumBoxCtrl from './controllers/album-box.controller.js';

export default angular.module('msl.album', [
  router,
  dataStore,
])
  .config(albumPageRoute)
  .directive('albumsList', albumsList)
  .directive('albumBox', albumBox)
  .controller('albumBoxCtrl', albumBoxCtrl)
  .controller('albumCtrl', albumCtrl)
  .name;
