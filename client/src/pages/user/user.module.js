//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

//Modules
import dataStore from 'modules/datastore/module';
import songsTable from 'modules/songs-table/module';

import userRoute from './user.route.js';
import userHistoryCtrl from './controllers/user-history.controller.js';
import userLibraryCtrl from './controllers/user-library.controller.js';
import userLikesCtrl from './controllers/user-likes.controller.js';

export default angular.module('msl.user', [
  uiRouter,
  dataStore,
  songsTable,
])
  .controller('userHistoryCtrl', userHistoryCtrl)
  .controller('userLibraryCtrl', userLibraryCtrl)
  .controller('userLikesCtrl', userLikesCtrl)
  .config(userRoute)
  .name;
