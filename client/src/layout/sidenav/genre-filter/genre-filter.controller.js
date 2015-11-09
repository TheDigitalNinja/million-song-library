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
   */
  constructor($scope, $log, $location, facetStore, filterModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.$location = $location;
    this.facetStore = facetStore;
    this.filterModel = filterModel;

    this._getGenreFacets();
  }

  /**
   * Determines if the given genre is the active one
   * @param {string} genreId
   * @return {Boolean}
   */
  activeGenre(genreId) {
    return this.selectedGenre === genreId;
  }

  /**
   * Applies genre filter on change
   * @param {string} genreId
   */
  applyFilterByGenre(genreId) {
    this.$location.search('genre', genreId);
    this.selectedGenre = genreId;
    this.filterModel.setSelectedGenre(genreId);
    this.filterModel.filter(this.$scope.listener);
  }

  /**
   * Gets all genres
   * @private
   */
  async _getGenreFacets() {
    try {
      const facetList = await this.facetStore.fetch(GENRE_FACET_ID);
      this.genres = facetList.children;
      this.$scope.$evalAsync();
    }
    catch(err) {
      this.genres = [];
      this.$log.warn(err);
    }
  }

}
