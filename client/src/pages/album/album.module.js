//Dependencies
import angular from 'angular';
import router from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';
import authentication from 'modules/authentication/module';

import albumPageRoute from './album.route.js';
import albumCtrl from './controllers/album.controller.js';
import albumBoxCtrl from './controllers/album-box.controller.js';

import albumsList from './directives/albums-list.js';
import albumBox from './directives/album-box.js';

import albumModel from './models/album.model.js';

export default angular.module('msl.album', [
  router,
  dataStore,
  authentication,
])
  .config(albumPageRoute)
  .directive('albumsList', albumsList)
  .directive('albumBox', albumBox)
  .controller('albumCtrl', albumCtrl)
  .controller('albumBoxCtrl', albumBoxCtrl)
  .factory('albumModel', albumModel)
  .name;
