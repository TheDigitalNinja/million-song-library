/**
 * Genre filter controller
 * @param {$scope} $scope
 * @param {$log} $log
 * @param {genreStore} genreStore
 * @param {genreFilterModel}  genreFilterModel
 */
class genreFilterCtrl {
  /*@ngInject*/

  constructor($scope, $log, genreStore, genreFilterModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.genreStore = genreStore;
    this.genreFilterModel = genreFilterModel;

    this.getGenres();
  }

  /**
   * Determines if the given genre is the active one
   * @param {GenreEntity} genre
   * @return {Boolean}
   */
  activeGenre(genre) {
    if(this.genreFilterModel.selectedGenre) {
      return this.genreFilterModel.selectedGenre.toLowerCase() === genre.genreName.toLowerCase();
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
    this.genreFilterModel.selectedGenre = genre;
    this.genreFilterModel.songs = null;
    this.genreFilterModel.albums = null;
    this.genreFilterModel.artists = null;
    this.genreFilterModel.getSongsFilteredByGenre(this.$scope);
    this.genreFilterModel.getAlbumsFilteredByGenre(this.$scope);
    this.genreFilterModel.getArtistsFilteredByGenre(this.$scope);
  }

  /**
   * Gets all genres
   */
  getGenres() {
    (async () => {
      try {
        const genresList = await this.genreStore.fetch(this.$scope.activeGenre);
        this.genres = genresList.genres;
        this.$scope.$evalAsync();
      }
      catch (err) {
        this.genres = [];
        this.$log.warn(err);
      }
    })();
  }

}

/**
 * Genre filter directive
 */
export default class GenreFilter {
  /*@ngInject*/

  constructor($q) {
    this.restrict = 'E';
    this.template = require('./genre_filter.html');
    this.scope = {
      activeGenre: '@',
    };
    this.controller = genreFilterCtrl;
    this.controllerAs = 'vm';
  }

  static directiveFactory($q) {
    GenreFilter.instance = new GenreFilter();
    return GenreFilter.instance;
  }
}
