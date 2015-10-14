/**
 * Song page controller
 */
export default class songCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {artistModel} artistModel
   * @param {$logProvider.$log} $log
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {songModel} songModel
   */
  constructor(artistModel, $log, $scope, $state, $stateParams, songModel) {
    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      this.artistModel = artistModel;
      this.$log = $log;
      this.$scope = $scope;
      this.songId = $stateParams.songId;
      this.model = songModel;
      //Initialization
      songModel.getSong(this.songId);
      $scope.$watch(()=> songModel.song,
        () => {
          if (songModel.song !== null) {
            artistModel.getSimilarArtists(songModel.song.artistMbid);
          }
        });
    }
    else {
      $state.go('msl.home');
    }
  }
}
