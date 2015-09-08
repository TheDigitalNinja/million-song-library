/**
 * Song page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {songStore} songStore
 */
export default class songCtrl {
  /*@ngInject*/

  constructor($state,
              $stateParams) {
    var vm = this;
    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      vm.songId = $stateParams.songId;
      this.getSongInfo();
    } else {
      $state.go('msl.home');
    }
  }

  // Fetch content from song store
  getSongInfo($scope,
              songStore) {
    (async() => {
      this.songInfo = await songStore.fetch(this.songId);
      $scope.$evalAsync();
    })();
  }

}
