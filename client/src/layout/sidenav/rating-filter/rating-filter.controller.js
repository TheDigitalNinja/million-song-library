import {RATING_FACET_ID} from '../../../constants.js';
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
   * @param {facetStore} facetStore
   * @param {$log} $log
   */
  constructor($location, $scope, filterModel, facetStore, $log) {
    this.$scope = $scope;
    this.$location = $location;
    this.filterModel = filterModel;
    this.facetStore = facetStore;
    this.$log = $log;

    this._getRatingFacets();
  }

  /**
   * Determines if the given rating is the active one
   * @param {string} ratingId
   * @return {Boolean}
   */
  isActiveRating(ratingId) {
    return ratingId === this.filterModel.selectedRating;
  }

  /**
   * Applies rating filter on change
   * @param {string} ratingId
   */
  applyRatingFilter(ratingId) {
    this.filterModel.setSelectedRating(ratingId);
    this.filterModel.filter(this.$scope.listener);
    this.$location.search('rating', this.filterModel.selectedRating);
    this.$location.search('genre', this.filterModel.selectedGenre);
  }

  /**
   * Gets the the star list from a particular rating
   * @param {Object} rating
   * @returns {Object[]}
   */
  getStars(rating) {
    try {
      let chars = rating.name.split(' ');
      let index = parseInt(chars[0]);
      if(isNaN(index)) {
        throw 'NaN';
      }
      return this._updateStars(index);
    } catch(error) {
      this.$log.error(error);
    }
  }

  /**
   * Update the stars for each rate
   * @param {int} starRating
   * @returns {Object[]}
   * @private
   */
  _updateStars(starRating) {
    const max = 5;
    let stars = [];

    for(let i = 0; i < max; i++) {
      stars.push({
        filled: i < starRating,
      });
    }
    return stars;
  }

  /**
   * Gets all rating facets
   * @private
   */
  async _getRatingFacets () {
    try {
      const facetList = await this.facetStore.fetch(RATING_FACET_ID);
      this.ratingFacets = facetList.children;
      this.$scope.$evalAsync();
    } catch(error) {
      this.$log.error(error);
    }
  }
}
