export default class libraryCtrl {
  constructor($scope, $log, myLibraryStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;

    this.getLibrarySongs();
  }

  getLibrarySongs() {
    (async () => {
      try {
        const response = await this.myLibraryStore.fetch();
        this.songs = response.songs;
        this.$scope.$evalAsync();
      }
      catch(error) {
        this.$log.warn(error);
      }
    })();
  }
}
