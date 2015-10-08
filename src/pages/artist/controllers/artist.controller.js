/**
 * Artist page controller
 */

export default class artistCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {ui.router.state.$state} $state
   * @param {$logProvider.$log} $log
   * @param {artistStore} artistStore
   * @param {catalogStore} catalogStore
   */
  constructor($scope, $stateParams, $state, $log, artistStore, catalogStore) {
    if (angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      this.artistId = $stateParams.artistId;
      this.$scope = $scope;
      this.$log = $log;
      this.artistStore = artistStore;
      this.catalogStore = catalogStore;
      this.activeTab = 'songs';
      this.getArtistInfo();
      this.getSimilarArtists();
    }
    else {
      $state.go('msl.home');
    }
  }

  /**
   * @private
   */
  getArtistInfo() {
    (async() => {
      try {
        this.artistInfo = await this.artistStore.fetch(this.artistId);
        this.artistSongs = await this.catalogStore.fetch({ artist: this.artistId });
        this.artistAlbums = await this.artistStore.fetchArtistAlbums(this.artistId);
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

  /**
   * @private
   */
  getSimilarArtists() {
    (async() => {
      try {
        const artistList = await this.artistStore.fetchSimilarArtist(this.artistId);
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

