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

  constructor($stateParams, $state) {
    this.artistInfo = {lala : 'asdfadfadsf'};
    if (angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      this.artirstId = $stateParams.artistId;
      this.getArtistInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

  getArtistInfo($log, artistStore, catalogStore, $scope) {
    (async() => {
      try {
        this.artistInfo = await artistStore.fetch(this.artistId);
        // TODO: Get list of artist albums
        this.artistSongs = await catalogStore.fetch({artist: this.artistId});
        this.displaySongs = true;
        $scope.$evalAsync();
      } catch (err) {
        // TODO: Handle the error
        $log.warn(err);
      }
    })();
  }

}

