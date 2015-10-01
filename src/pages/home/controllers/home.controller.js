/**
 * Home page controller
 * @param {$rootScope.Scope} $scope
 * @param {$log} $log
 * @param {catalogStore} catalogStore
 * @param {myLibraryStore} myLibraryStore
 * @param {artistStore} artistStore
 * @param {albumStore} albumStore
 */
export default class homeCtrl {
  /*@ngInject*/

  constructor($scope, $log, catalogStore, myLibraryStore, artistStore, albumStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistStore = artistStore;
    this.albumStore = albumStore;
    this.myLibraryStore = myLibraryStore;
    this.catalogStore = catalogStore;

    this.categories = [];
    // Mockup some data

    // TODO: Remove mock data when we start getting list of genres from backend
    this.categories.push({
      title: 'Artists',
      items: [{name: 'Artist Name'}, {name: 'Artist Name 2'}, {name: 'Artist Name 3'}],
    });

    //Initializes data
    this.getSongs();
    this.getArtists();
    this.getAlbums();
  }

  getNumber(num) {
    return new Array(num);
  }

  getAlbums() {
    (async () => {
      try {
        const albumList = await this.albumStore.fetchAll();
        this.albumsList = albumList.albums;
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.$log.warn(error);
      }
    })();
  }

  getSongs() {
    (async () => {
      try {
        const songList = await this.catalogStore.fetch();
        this.songs = songList.songs;
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.$log.warn(error);
      }
    })();
  }

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

