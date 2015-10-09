/**
 * Song page controller
 */
export default class songCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$rootScope.Scope} $scope
   * @param {$logProvider.$log} $log
   * @param {ui.router.state.$state} $state
   * @param {songStore} songStore
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {artistStore} artistStore
   */
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

  /**
   * @private
   */
  async getSongInfo() {
    try {
      this.songInfo = await this.songStore.fetch(this.songId);
      this.$scope.$evalAsync();
      this.getSimilarArtists(this.songInfo.artistMbid);
    } catch (err) {
      // TODO: Handle the error
      this.songInfo = {};
      this.$log.warn(err);
    }
  }

  /**
   * Gets similar artists
   * @param {int} artistId
   */
  async getSimilarArtists(artistId) {
    try {
      const artistList = await this.artistStore.fetchSimilarArtist(artistId);
      this.similarArtists = artistList.artists;
      this.$scope.$evalAsync();
    } catch (err) {
      // TODO: Handle the error
      this.similarArtists = [];
      this.$log.warn(err);
    }
  }

}
