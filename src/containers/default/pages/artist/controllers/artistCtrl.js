/**
 * Artist page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {ui.router.state.$stateParams} $stateParams
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$logProvider.$log} $log
 */
function artistCtrl (
  $scope,
  $state,
  $stateParams,
  artistStore,
  catalogStore,
  $log
) {
  // Get artistId from $stateParams
  this.artistId = ($stateParams.artistId) ? $stateParams.artistId : "";

  if (this.artistId !== "") {
    // Fetch content from artist store
    (async () => {
      try {
        this.artistInfo = await artistStore.fetch(this.artistId);
        // TODO: Get list of artist albums
        this.artistSongs = await catalogStore.fetch({artist: this.artistId});
        this.displaySongs = true;
        $scope.$evalAsync();
      } catch (err) {
        // TODO: Handle the error
        $log.warn(err);
      }
    })();
  } else {
    $state.go("default.home");
  }
}

export default artistCtrl;
