import _ from 'lodash';
import ratingTemplate from '../templates/star-rating.html';
import ratingController from '../controllers/star-rating.controller.js';

function starRatingDirective () {
  'ngInject';

  return {
    restrict: 'A',
    template: ratingTemplate,
    controller: ratingController,
    controllerAs: 'rating',
    scope: {
      starRating: '=',
      personalRating: '=',
      readOnly: '=',
      entityId: '=',
      entityType: '@',
    },
  };
}

export default starRatingDirective;
