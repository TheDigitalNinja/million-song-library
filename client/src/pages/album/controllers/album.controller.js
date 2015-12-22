/**
 * Album page controller
 * @author anram88
 */
export default class albumCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {albumModel} albumModel
   * @param {artistModel} artistModel
   * @param {$logProvider.$log} $log
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {authentication} authentication
   */
  constructor(albumModel, artistModel, $log, $scope, $state, $stateParams, authentication) {
    if(angular.isDefined($stateParams.albumId) && $stateParams.albumId.length > 0) {
      this.$scope = $scope;
      this.$log = $log;
      this.artistModel = artistModel;
      this.model = albumModel;
      this.albumId = $stateParams.albumId;
      this.authentication = authentication;

      //Initialization
      albumModel.getAlbum(this.albumId);
      $scope.$watch(() => albumModel.album,
        () => {
          if(albumModel.album !== null) {
            albumModel.getAlbumSongs(albumModel.album.artistId, (songs) => {
              this.albumSongs = songs;
            });
            this.displaySongs = true;
            artistModel.getSimilarArtists(albumModel.album.artistId, (artists) => {
              this.similarArtists = artists;
              this.$scope.$evalAsync();
            });
          }
        }
      );
    }
    else {
      $state.go('msl.home');
    }
  }
}
