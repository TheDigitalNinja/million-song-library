import _ from 'lodash';
import ratingTemplate from '../templates/star-rating.html';

function ratingController ($scope, rateStore, $log) {
  'ngInject';

  this.max = 5;

  this.readOnly = true;
  if (_.isUndefined($scope.readOnly) || $scope.readOnly === false) {
    this.readOnly = false;
  }

  var updateStars = () => {
    this.stars = [];
    for (let i = 0; i < this.max; i++) {
      this.stars.push({
        filled: i < $scope.starRating
      });
    }
  };

  this.rate = async (index) => {
    if (!this.readOnly && $scope.starRating !== (index + 1)) {
      this.readOnly = true;
      $scope.starRating = index + 1;
      try {
        await rateStore.push($scope.songId, $scope.starRating);
      } catch (err) {
        // TODO: Handle the error
        $log.warn(err);
      }
      this.readOnly = false;
      $scope.$evalAsync();
    }
  };

  $scope.$watch('starRating', function (newVal) {
    if (newVal >= 0) {
      updateStars();
    }
  });
}

function starRatingDirective () {
  'ngInject';

  return {
    restrict: 'A',
    template: ratingTemplate,
    controller: ratingController,
    controllerAs: 'rating',
    scope: {
      starRating: '=',
      readOnly: '=',
      songId: '='
    }
  };
}

export default starRatingDirective;
