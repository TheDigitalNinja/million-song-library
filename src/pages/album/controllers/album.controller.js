/**
 * Album page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {albumStore} albumStore
 * @param {catalogStore} catalogStore
 * @param {$logProvider.$log} $log
 */
export default class albumCtrl {
  /*@ngInject*/

  constructor($scope, $state, albumStore, $log, $stateParams, catalogStore) {
    if (angular.isDefined($stateParams.albumId) && $stateParams.albumId.length > 0) {
      this.$scope = $scope;
      this.$log = $log;
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
        this.albumSongs = await this.catalogStore.fetch({ artist: this.albumInfo.artistId });
        this.displaySongs = true;
        this.$scope.$evalAsync();
      } catch (err) {
        // TODO: Handle the error
        this.albumInfo = {};
        this.displaySongs = false;
        this.$log.warn(err);
      }
    })();
  }

}
