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
   * @param {genreStore} genreStore
   * @param {filterModel} filterModel
   */
  constructor($scope, $log, $location, genreStore, filterModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.$location = $location;
    this.genreStore = genreStore;
    this.filterModel = filterModel;

    this.getGenres();
  }

  /**
   * Determines if the given genre is the active one
   * @param {GenreEntity} genre
   * @return {Boolean}
   */
  activeGenre(genre) {
    if(this.selectedGenre) {
      return this.selectedGenre.toLowerCase() === genre.name.toLowerCase();
    }
    else {
      return false;
    }
  }

  /**
   * Applies genre filter on change
   * @param {string} genre
   */
  applyFilterByGenre(genre) {
    this.$location.search('genre', genre);
    this.selectedGenre = genre;

    this.filterModel.filter({ genre: genre }, this.$scope.listener);
  }

  /**
   * Gets all genres
   */
  async getGenres() {
    try {
      const genresList = await this.genreStore.fetch(this.$scope.activeGenre);
      this.genres = genresList.children;
      this.$scope.$evalAsync();
    }
    catch(err) {
      this.genres = [];
      this.$log.warn(err);
    }
  }

}
