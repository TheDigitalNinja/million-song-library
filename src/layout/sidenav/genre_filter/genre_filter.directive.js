class genreFilterCtrl {
  /*@ngInject*/

  constructor($scope, $log, genreStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.genreStore = genreStore;

    this.getGenres();
  }

  getGenres() {
    (async () => {
      try {
        const genresList = await this.genreStore.fetch(this.$scope.activeGenre);
        this.genres = genresList.genres;
        this.$scope.$evalAsync();
      }
      catch(err) {
        this.genres = [];
        this.$log.warn(err);
      }
    })();
  }
}

class GenreFilter {
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

export default GenreFilter;
