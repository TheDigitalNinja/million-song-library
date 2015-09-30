/**
 * Artist page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$logProvider.$log} $log
 */

export default class artistCtrl {
  /*@ngInject*/

  constructor($stateParams, $state, $log, artistStore, catalogStore, $scope) {
    if (angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      this.artistId = $stateParams.artistId;
      this.$scope = $scope;
      this.$log = $log;
      this.artistStore = artistStore;
      this.catalogStore = catalogStore;
      this.artistStore = artistStore;
      this.getArtistInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

  getArtistInfo() {
    (async() => {
      try {
        this.artistInfo = await this.artistStore.fetch(this.artistId);
        // TODO: Get list of artist albums
        this.artistSongs = await this.catalogStore.fetch({artist: this.artistId});
        this.displaySongs = true;
        this.$scope.$evalAsync();
      } catch (err) {
        // TODO: Handle the error
        this.artistInfo = {};
        this.artistSongs = [];
        this.displaySongs = false;
        this.$log.warn(err);
      }
    })();
  }

}

