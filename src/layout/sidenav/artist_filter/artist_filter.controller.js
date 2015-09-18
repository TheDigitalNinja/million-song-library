export default class artistFilterCtrl {
  /*@ngInject*/

  constructor($scope, $log, artistStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistStore = artistStore;

    this.getArtists();
  }

  getArtists() {
    (async () => {
      try {
        const artistList = await this.artistStore.fetchAll();
        this.artists = artistList.artists;
        this.$scope.$evalAsync();
      }
      catch(err) {
        this.artists = [];
        this.$log.warn(err);
      }
    })();
  }
}
