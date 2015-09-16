/**
 * Song page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {songStore} songStore
 */
export default class songCtrl {
  /*@ngInject*/

  constructor($scope,
              $state,
              songStore,
              $stateParams) {

    // Fetch content from song store
    this.getSongInfo = async() => {
      this.songInfo = await songStore.fetch(this.songId);
      $scope.$evalAsync();
    };

    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      this.songId = $stateParams.songId;
      this.getSongInfo();
    }
    else {
      $state.go('msl.home');
    }
  }

}
