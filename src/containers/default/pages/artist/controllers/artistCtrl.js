function artistCtrl ($scope, $state, $stateParams, artistStore) {
  this.artistId = ($stateParams.artistId) ? $stateParams.artistId : "";

  if (this.artistId !== "") {
    // Fetch content from artist store
    (async () => {
      this.artistInfo = await artistStore.fetch(this.artistId);
      // TODO: Get artis's albums info
      // TODO: Get artis's songs info
      $scope.$evalAsync();
    })();
  } else {
    $state.go("default.home");
  }
}

export default artistCtrl;
