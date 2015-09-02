import './stylesheets/default.less';
import angular from 'angular';
import starRating from './directives/star-rating';
import datastore from 'modules/datastore/module';

export default angular.module('msl.rating', [datastore])
  .directive('starRating', starRating)
  .name;
