/**
 * Song page controller
 */
export default class songCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {artistModel} artistModel
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {songModel} songModel
   */
  constructor(artistModel, $scope, $state, $stateParams, songModel) {
    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      this.artistModel = artistModel;
      this.$scope = $scope;
      this.songId = $stateParams.songId;
      this.model = songModel;
      //Initialization
      this._getSong();
    }
    else {
      $state.go('msl.home');
    }
  }

  /**
   * @private
   * Gets the song using the songId
   */
  _getSong() {
    this.model.getSong(this.songId, (song) => {
      this.song = song;
      this._getSimilarArtists(song);
    });
  }

  /**
   * @private
   * Gets the similar artists of the song's artist
   * @param {SongInfoEntity} song
   */
  _getSimilarArtists(song) {
    this.artistModel.getSimilarArtists(song.artistId, (artists) => {
      this.similarArtists = artists;
    });
  }
}
