/**
 * Album page controller
 * @author anram88
 */
export default class albumCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {albumStore} albumStore
   * @param {artistStore} artistStore
   * @param {catalogStore} catalogStore
   * @param {$logProvider.$log} $log
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {ui.router.state.$stateParams} $stateParams
   */
  constructor(albumStore, artistStore, catalogStore, $log, $scope, $state, $stateParams) {
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

  /**
   * @private
   */
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

  /**
   * Gets similar artists
   * @param {int} artistId
   */
  getSimilarArtists(artistId) {
    (async() => {
      try {
        const artistList = await this.artistStore.fetchSimilarArtist(artistId);
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
