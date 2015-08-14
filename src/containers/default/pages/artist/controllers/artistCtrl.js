function artistCtrl ($scope, $state, $stateParams, artistStore, catalogStore) {
  this.artistId = ($stateParams.artistId) ? $stateParams.artistId : "";

  if (this.artistId !== "") {
    // Fetch content from artist store
    (async () => {
      this.artistInfo = await artistStore.fetch(this.artistId);
      $scope.$evalAsync();
    })().then(async () => {
      // TODO: Get list of artist albums
      this.artistSongs = await catalogStore.fetch({artist: this.artistId});
      $scope.$evalAsync();
    });
  } else {
    $state.go("default.home");
  }
}

export default artistCtrl;
