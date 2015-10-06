/**
 * Home page controller
 * @param {$rootScope.Scope} $scope
 * @param {$log} $log
 * @param {catalogStore} catalogStore
 * @param {myLibraryStore} myLibraryStore
 * @param {artistStore} artistStore
 * @param {albumStore} albumStore
 * @param {genreFilterModel} genreFilterModel
 */
export default class homeCtrl {
  /*@ngInject*/

  constructor($scope,
              $log,
              catalogStore,
              myLibraryStore,
              artistStore,
              albumStore,
              genreFilterModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistStore = artistStore;
    this.albumStore = albumStore;
    this.myLibraryStore = myLibraryStore;
    this.catalogStore = catalogStore;
    this.model = genreFilterModel.selectedGenre;

    //Initializes data
    this.getSongs();
    this.getArtists();
    this.getAlbums();

    this.$scope.$watch(() => genreFilterModel.songs,
      () => {
        if (genreFilterModel.songs !== null) {
          this.songs = genreFilterModel.songs;
        }
      });

    this.$scope.$watch(()=> genreFilterModel.artists,
      () => {
        if (genreFilterModel.artists !== null) {
          this.artists = genreFilterModel.artists;
        }
      });

    this.$scope.$watch(()=> genreFilterModel.albums,
      () => {
        if (genreFilterModel.albums !== null) {
          this.albumsList = genreFilterModel.albums;
        }
      });
  }

  /**
   * Fetches all ablums
   */
  getAlbums() {
    (async () => {
      try {
        const albumList = await this.albumStore.fetchAll();
        this.albumsList = albumList.albums;
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.albumsList = [];
        this.$log.warn(error);
      }
    })();
  }

  /**
   * Gets all songs
   */
  getSongs() {
    (async () => {
      try {
        const songList = await this.catalogStore.fetch();
        this.songs = songList.songs;
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.songs = [];
        this.$log.warn(error);
      }
    })();
  }

  /**
   * Gets all artists
   */
  getArtists() {
    (async () => {
      try {
        const artistList = await this.artistStore.fetchAll();
        this.artists = artistList.artists;
        this.$scope.$evalAsync();
      }
      catch (err) {
        this.artists = [];
        this.$log.warn(err);
      }
    })();
  }

  /**
   * Adds selected song to library
   * @param {int} songId
   */
  addToMyLibrary(songId) {
    (async () => {
      try {
        await this.myLibraryStore.addSong(songId);
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.$log.warn(error);
      }
    })();
  }
}

