/**
 * Artist page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$logProvider.$log} $log
 */
export default function artistCtrl($scope,
                                   $state,
                                   $stateParams,
                                   artistStore,
                                   catalogStore,
                                   $log) {

  var vm = this;

  // Fetch artist data
  var getArtistInfo = async() => {
    try {
      vm.artistInfo = await artistStore.fetch(vm.artistId);
      // TODO: Get list of artist albums
      vm.artistSongs = await catalogStore.fetch({artist: vm.artistId});
      vm.displaySongs = true;
      $scope.$evalAsync();
    } catch (err) {
      // TODO: Handle the error
      $log.warn(err);
    }
  };

  function init() {
    // Get artistId from $stateParams
    if (angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      vm.artirstId = $stateParams.artistId;
      getArtistInfo();
    } else {
      $state.go('msl.home');
    }
  }

  init();
}
