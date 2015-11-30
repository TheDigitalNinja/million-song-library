import './stylesheets/default.scss';
import angular from 'angular';
import starRating from './directives/star-rating';
import datastore from 'modules/datastore/module';
import ratingModel from './models/rating.model';
import loginModal from 'modules/login-modal/login-modal.module';
import Permission from 'modules/permission/module';

export default angular.module('msl.rating', [
    datastore,
    loginModal,
    Permission,
  ])
  .service('ratingModel', ratingModel)
  .directive('starRating', starRating)
  .name;
