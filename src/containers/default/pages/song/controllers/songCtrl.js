/**
 * Song page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {songStore} songStore
 */
function songCtrl (
  $scope,
  $state,
  $stateParams,
  songStore
) {
  "ngInject";

  // Get songId from $stateParams
  this.songId = ($stateParams.songId) ? $stateParams.songId : "";

  // Fetch content from song store
  this.getSongInfo = async () => {
    this.songInfo = await songStore.fetch(this.songId);
    $scope.$evalAsync();
  };

  if (this.songId !== "") {
    this.getSongInfo();
  } else {
    $state.go("default.home");
  }
}

export default songCtrl;
