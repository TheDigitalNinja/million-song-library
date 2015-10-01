/**
 * Album page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {albumStore} albumStore
 */
export default class albumCtrl {
  /*@ngInject*/

  constructor($scope,
              $state,
              albumStore,
              $stateParams) {

    // Fetch content from song store
    this.getAlbumInfo = async() => {
      this.albumInfo = await albumStore.fetch(this.albumId);
      $scope.$evalAsync();
    };

    if (angular.isDefined($stateParams.albumId) && $stateParams.albumId.length > 0) {
      this.albumId = $stateParams.albumId;
      this.getAlbumInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

}
