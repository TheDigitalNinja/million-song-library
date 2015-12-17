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
   * @param {authentication} authentication
   */
  constructor(artistModel, $scope, $state, $stateParams, songModel, authentication) {
    if(angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      this.authentication = authentication;
      this.artistModel = artistModel;
      this.songId = $stateParams.songId;
      this.$scope = $scope;
      this.model = songModel;
      //Initialization
      this._getSong();
    }
    else {
      $state.go('msl.home');
    }
  }

  /**
   * Gets the song using the songId
   * @private
   */
  _getSong() {
    this.model.getSong(this.songId, (song) => {
      this.song = song;
      this._getSimilarArtists(song);
    });
  }

  /**
   * Gets the similar artists of the song's artist
   * @param {SongInfoEntity} song
   * @private
   */
  _getSimilarArtists(song) {
    this.artistModel.getSimilarArtists(song.artistId, (artists) => {
      this.similarArtists = artists;
      this.$scope.$evalAsync();
    });
  }
}
