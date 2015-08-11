import _ from "lodash";
import ratingTemplate from "../templates/star-rating.html";

function ratingController ($scope) {
  "ngInject";

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

  this.rate = (index) => {
    if (!this.readOnly) {
      $scope.starRating = index + 1;
      // TODO: make a request to back-end server
    }
  };

  $scope.$watch("starRating", function (oldVal, newVal) {
    if (newVal >= 0) {
      updateStars();
    }
  });
}

function starRatingDirective () {
  "ngInject";

  return {
    restrict: "A",
    template: ratingTemplate,
    controller: ratingController,
    controllerAs: "rating",
    scope: {
      starRating: "=",
      readOnly: "="
    }
  };
}

export default starRatingDirective;
