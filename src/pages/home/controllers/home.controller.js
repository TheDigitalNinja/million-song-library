/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  constructor($scope, catalogStore, genreStore, albumStore, $log) {

    this.$scope = $scope;
    this.$log = $log;
    this.albumStore = albumStore;
    this.catalogStore = catalogStore;
    this.myLibraryStore = myLibraryStore;
    this.categories = [];
    // Mockup some data

    // TODO: Remove mock data when we start getting list of genres from backend
    this.categories.push({
      title: 'Artists',
      items: [{name: 'Artist Name'}, {name: 'Artist Name 2'}, {name: 'Artist Name 3'}],
    });

    this.getSongs();
  }

  getNumber(num) {
    return new Array(num);
  }

  getSongs() {
    (async () => {
      try {
        const songList = await this.catalogStore.fetch();
        const albumList = await this.albumStore.fetchAll();
        this.albumsList = albumList.albums;
        this.songs = songList.songs;
        this.$scope.$evalAsync();
      }
      catch (error) {
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
      catch (error) {
        this.$log.warn(error);
      }
    })();
  }
}

