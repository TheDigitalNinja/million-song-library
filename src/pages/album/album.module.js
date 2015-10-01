//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';

import albumPageRoute from './album.route.js';
import albumCtrl from './controllers/album.controller.js';
import albumsList from './directives/albums-list.js';

export default angular.module('msl.album', [
  router,
  dataStore,
])
  .config(albumPageRoute)
  .directive('albumsList', albumsList)
  .controller('albumCtrl', albumCtrl)
  .name;
