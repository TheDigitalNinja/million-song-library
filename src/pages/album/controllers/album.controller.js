/**
 * Album page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {artistStore} artistStore
 * @param {albumStore} albumStore
 * @param {catalogStore} catalogStore
 * @param {$logProvider.$log} $log
 */
export default class albumCtrl {
  /*@ngInject*/

  constructor($scope, $state, artistStore, albumStore, $log, $stateParams, catalogStore) {
    if (angular.isDefined($stateParams.albumId) && $stateParams.albumId.length > 0) {
      this.$scope = $scope;
      this.$log = $log;
      this.artistStore = artistStore;
      this.albumStore = albumStore;
      this.catalogStore = catalogStore;
      this.albumId = $stateParams.albumId;
      this.getAlbumInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

  getAlbumInfo() {
    (async() => {
      try {
        this.albumInfo = await this.albumStore.fetch(this.albumId);
        this.albumSongs = [];
        this.displaySongs = true;
        this.$scope.$evalAsync();
        this.getSimilarArtists(this.albumInfo.artistId);
      } catch (err) {
        // TODO: Handle the error
        this.albumInfo = {};
        this.albumSongs = [];
        this.displaySongs = false;
        this.$log.warn(err);
      }
    })();
  }

  getSimilarArtists(artistMbid) {
    (async() => {
      try {
        const artistList = await this.artistStore.fetchSimilarArtist(artistMbid);
        this.similarArtists = artistList.artists;
        this.$scope.$evalAsync();
      } catch (err) {
        // TODO: Handle the error
        this.similarArtists = [];
        this.$log.warn(err);
      }
    })();
  }
}
