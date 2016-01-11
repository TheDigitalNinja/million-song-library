import { ROLE_USER } from '../../../constants.js';

/**
 * start rating controller
 */
export default class ratingController {
  /*@ngInject*/

  /**
   * @constructor
   * @this {rating}
   * @param {$rootScope.Scope} $scope
   * @param {ratingModel} ratingModel
   * @param {$log} $log
   * @param {loginModal} loginModal
   * @param {Permission} Permission
   */
  constructor($scope, ratingModel, $log, loginModal, Permission) {
    this.$scope = $scope;
    this.ratingModel = ratingModel;
    this.$log = $log;
    this.loginModal = loginModal;
    this.Permission = Permission;

    this.max = 5;
    this.readOnly = !!$scope.readOnly;

    this._updateStarRating();
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
        await this.Permission.authorize({ only: [ROLE_USER] });

        this.ratingModel.rate(this.$scope.entityId, this.$scope.entityType, newRating);
        this._setStarRating(newRating);
      } catch(err) {
        this.loginModal.show();
        this.$log.warn(err);
      }
      this.readOnly = false;
      this.$scope.$evalAsync();
    }
  }

  /**
   * Update the star rating checking the personal rating
   * @private
   */
  _updateStarRating() {
    if(this.$scope.personalRating && this.$scope.personalRating > 0) {
      this.$scope.starRating = this.$scope.personalRating;
      this.isPersonalRating = true;
    }
    else {
      this.isPersonalRating = false;
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
