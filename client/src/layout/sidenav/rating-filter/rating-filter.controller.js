/**
 * Rating filter controller
 */
export default class ratingFilterCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$location} $location
   * @param {$scope} $scope
   * @param {filterModel} filterModel
   */
  constructor($location, $scope, filterModel) {
    this.$scope = $scope;
    this.$location = $location;
    this.filterModel = filterModel;

    this.rates = [];

    this._updateRatings();
  }

  /**
   * Applies rating filter on change
   * @param {rate} rate
   */
  applyRatingFilter(rate) {
    this.ratingFilter = rate.rate;
    this.$location.search('rating', this.ratingFilter);
    this.filterModel.filter({ rating: this.ratingFilter }, this.$scope.listener);
  }

  /**
   * Updates the array of rates
   */
  _updateRatings() {
    const maxRates = 4;

    for (let i = maxRates; i > 0; i--) {
      this.rates.push({ rate: i, stars: this._updateStars(i) });
    }
  }

  /**
   * Update the stars for each rate
   */
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
