/**
 * Song page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {songStore} songStore
 */
 export default function songCtrl($scope,
                  $state,
                  $stateParams,
                  songStore) {
  'ngInject';

  var vm = this;

  // Fetch content from song store
  var getSongInfo = async() => {
    vm.songInfo = await songStore.fetch(vm.songId);
    $scope.$evalAsync();
  };

  function init() {
    if (angular.isDefined($stateParams.songId) && $stateParams.songId.length > 0) {
      vm.songId = $stateParams.songId;
      getSongInfo();
    } else {
      $state.go('msl.home');
    }
  }

  init();

}
