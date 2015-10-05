/**
 * Rating filter controller
 * @param {$location} $location
 * @param {$scope} $scope
 */
export default class ratingFilterCtrl {
  /*@ngInject*/

  constructor($location, $scope) {
    this.$scope = $scope;
    this.rates = [];
    this.$location = $location;

    this._updateRatings();
  }

  /**
   * Applies rating filter on change
   * @param {rate} rate
   */
  applyRatingFilter(rate) {
    this.$scope.ratingFilter = rate.rate;
  }

  _updateRatings() {
    const maxRates = 4;

    for (let i = maxRates; i > 0; i--) {
      this.rates.push({ rate: i, stars: this._updateStars(i) });
    }
  }

  _updateStars(starRating) {
    const max = 5;
    let stars = [];

    for (let i = 0; i < max; i++) {
      stars.push({
        filled: i < starRating,
      });
    }
    return stars;
  }
}
