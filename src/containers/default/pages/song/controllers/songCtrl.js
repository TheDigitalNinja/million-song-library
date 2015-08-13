function songCtrl ($scope, $state, $stateParams, songStore) {
  "ngInject";

  this.songId = ($stateParams.songId) ? $stateParams.songId : "";

  if (this.songId !== "") {
    // Fetch content from song store
    (async () => {
      this.songInfo = await songStore.fetch(this.songId);
      $scope.$evalAsync();
    })();
  } else {
    $state.go("default.home");
  }
}

export default songCtrl;
