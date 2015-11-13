import './stylesheets/default.scss';
import angular from 'angular';
import starRating from './directives/star-rating';
import datastore from 'modules/datastore/module';
import ratingModel from './models/rating.model';

export default angular.module('msl.rating', [datastore])
  .service('ratingModel', ratingModel)
  .directive('starRating', starRating)
  .name;
