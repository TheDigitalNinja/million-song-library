/**
 * Song page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {artistStore} artistStore
 * @param {songStore} songStore
 * @param {$logProvider.$log} $log
 */
export default class songCtrl {
  /*@ngInject*/

  constructor($scope, $log, $state, songStore, $stateParams, artistStore) {
    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      this.$log = $log;
      this.$scope = $scope;
      this.songId = $stateParams.songId;
      this.songStore = songStore;
      this.artistStore = artistStore;
      this.getSongInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

  getSongInfo() {
    (async() => {
      try {
        this.songInfo = await this.songStore.fetch(this.songId);
        this.$scope.$evalAsync();
        this.getSimilarArtists(this.songInfo.artistMbid);
      } catch (err) {
        // TODO: Handle the error
        this.songInfo = {};
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
