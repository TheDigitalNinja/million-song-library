import {GENRE_FACET_ID} from '../../../constants.js';
/**
 * Genre filter controller
 */
export default class genreFilterCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$scope} $scope
   * @param {$log} $log
   * @param {$location} $location
   * @param {facetStore} facetStore
   * @param {filterModel} filterModel
   * @param {$filter} $filter
   */
  constructor($scope, $log, $location, facetStore, filterModel, $filter) {
    this.$scope = $scope;
    this.$log = $log;
    this.$location = $location;
    this.facetStore = facetStore;
    this.filterModel = filterModel;
    this.$filter = $filter;

    this._getGenreFacets();
  }

  /**
   * Determines if the given genre is the active one
   * @param {string} genreId
   * @return {Boolean}
   */
  activeGenre(genreId) {
    return this.filterModel.selectedGenreId === genreId;
  }

  /**
   * Applies genre filter on change
   * @param {object} genre
   */
  applyFilterByGenre(genre) {
    this.filterModel.setSelectedGenre(genre);
    this.filterModel.filter(this.$scope.listener);
    this.$location.search('rating', this.filterModel.selectedRating);
    this.$location.search('genre', this.filterModel.selectedGenreId);
  }

  /**
   * Gets all genres
   * @private
   */
  async _getGenreFacets() {
    try {
      const facetList = await this.facetStore.fetch(GENRE_FACET_ID);
      this.genres = this.$filter('orderBy')(facetList.children, 'name');
      this.$scope.$evalAsync();
    }
    catch(err) {
      this.genres = [];
      this.$log.warn(err);
    }
  }

}
