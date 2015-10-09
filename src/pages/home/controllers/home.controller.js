/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {albumStore} albumStore
   * @param {artistStore} artistStore
   * @param {catalogStore} catalogStore
   * @param {genreFilterModel} genreFilterModel
   * @param {$log} $log
   * @param {myLibraryStore} myLibraryStore
   * @param {$rootScope.Scope} $scope
   */
  constructor(albumStore,
              artistStore,
              catalogStore,
              genreFilterModel,
              $log,
              myLibraryStore,
              $scope) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistStore = artistStore;
    this.albumStore = albumStore;
    this.myLibraryStore = myLibraryStore;
    this.catalogStore = catalogStore;
    this.model = genreFilterModel.selectedGenre;
    this.activeTab = 'songs';

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

    this.$scope.$watch(() => genreFilterModel.artists,
      () => {
        if (genreFilterModel.artists !== null) {
          this.artists = genreFilterModel.artists;
        }
      });

    this.$scope.$watch(() => genreFilterModel.albums,
      () => {
        if (genreFilterModel.albums !== null) {
          this.albumsList = genreFilterModel.albums;
        }
      });
  }

  //TODO refactor this into its own model
  /**
   * Fetches all ablums
   * @private
   */
  async getAlbums() {
    try {
      const albumList = await this.albumStore.fetchAll();
      this.albumsList = albumList.albums;
      this.$scope.$evalAsync();
    }
    catch (error) {
      this.albumsList = [];
      this.$log.warn(error);
    }
  }

  //TODO refactor this into its own model
  /**
   * Gets all songs
   * @private
   */
  async getSongs() {
    try {
      const songList = await this.catalogStore.fetch();
      this.songs = songList.songs;
      this.$scope.$evalAsync();
    }
    catch (error) {
      this.songs = [];
      this.$log.warn(error);
    }
  }

  //TODO refactor this into its own model
  /**
   * Gets all artists
   * @private
   */
  async getArtists() {
    try {
      const artistList = await this.artistStore.fetchAll();
      this.artists = artistList.artists;
      this.$scope.$evalAsync();
    }
    catch (err) {
      this.artists = [];
      this.$log.warn(err);
    }
  }

  //TODO refactor this into its own model
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

