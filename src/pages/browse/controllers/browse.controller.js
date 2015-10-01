/**
 * Genre page controller
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {$rootScope.Scope} $scope
 * @param {catalogStore} catalogStore
 * @param {myLibraryStore} myLibraryStore
 */
export default class genreCtrl {
  /*@ngInject*/

  constructor($stateParams, $scope, $log, catalogStore, myLibraryStore) {
    this.$stateParams = $stateParams;
    this.$scope = $scope;
    this.$log = $log;
    this.catalogStore = catalogStore;
    this.myLibraryStore = myLibraryStore;

    this.genreName = $stateParams.genre;
    this.minRating = $stateParams.rating;
    this.artistMbid = $stateParams.artist;
    this.songs = [];

    this.getGenreSongs();
  }

  getGenreSongs() {
    (async () => {
      try {
        const songsList = await this.catalogStore.fetch({
          genre: this.genreName,
          rating: this.minRating,
          artist: this.artistMbid,
        });

        this.songs = songsList.songs;
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.songs = [];
        this.$log.warn(error);
      }
    })();
  }

  addToMyLibrary(songId) {
    (async () => {
      try {
        await this.myLibraryStore.addSong(songId);
        this.$scope.$evalAsync();
      }
      catch(error) {
        this.$log.warn(error);
      }
    })();
  }
}
