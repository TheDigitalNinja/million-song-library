//Styles
import './stylesheets/artist.less';

//Dependencies
import angular from 'angular';
import uiRouter from 'angular-ui-router';

//Module
import dataStore from 'modules/datastore/module';
import songsTable from 'modules/songs-table/module';

import artistRoute from './artist.route.js';
import artistCtrl from './controllers/artist.controller.js';
import artistListCtrl from './controllers/artist-list.controller.js';

export default angular.module('msl.artist', [
  uiRouter,
  dataStore,
  songsTable,
])
  .config(artistRoute)
  .controller('artistCtrl', artistCtrl)
  .controller('artistListCtrl', artistListCtrl)
  .name;
