/**
 * start rating controller
 */
export default class ratingController {
  /*@ngInject*/

  /**
   * @constructor
   * @this {rating}
   * @param {$rootScope.Scope} $scope
   * @param {rateStore} rateStore
   * @param {$log} $log
   */
  constructor($scope, rateStore, $log) {
    this.$scope = $scope;
    this.rateStore = rateStore;
    this.$log = $log;
    this.max = 5;
    this.readOnly = !!$scope.readOnly;
    this.isPersonalRating = $scope.isPersonalRating;

    $scope.$watch('starRating', (newVal) => this._updateStars());
  }

  /**
   * Update the stars using index
   * @param {number} index
   */
  async rate(index) {
    const newRating = index + 1;
    if(!this.readOnly && this.$scope.starRating !== newRating) {
      this.readOnly = true;
      try {
        await this.rateStore.push(this.$scope.songId, newRating);
        this._setStarRating(newRating);
      } catch(err) {
        this.$log.warn(err);
      }
      this.readOnly = false;
      this.$scope.$evalAsync();
    }
  }

  /**
   * Set the new star rating and update the stars
   * @param {number} rating
   * @private
   */
  _setStarRating(rating) {
    this.$scope.starRating = rating;
    this.isPersonalRating = true;
  }

  /**
   * Update the stars
   * @private
   */
  _updateStars() {
    this.stars = [];
    for(let i = 0; i < this.max; i++) {
      this.stars.push({
        filled: i < this.$scope.starRating,
      });
    }
  }
}
