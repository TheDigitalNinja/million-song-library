import angular from 'angular';
import uiRouter from 'angular-ui-router';

import browseRoute from './browse.route.js';
import browseCtrl from './controllers/browse.controller.js';
import dataStore from 'modules/datastore/module';

export default angular.module('msl.browse', [
    uiRouter,
    dataStore,
    ])
  .config(browseRoute)
  .controller('browseCtrl', browseCtrl)
  .name;
