function historyCtrl ($scope, recentSongsStore) {
  "ngInject";

  // fetch content from my history store
  (async () => {
    this.content = await recentSongsStore.fetch();
    this.display = true;
    $scope.$evalAsync();
  })();
}

export default historyCtrl;
