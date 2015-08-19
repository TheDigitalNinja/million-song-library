/**
 * my page history state controller
 * @param {$rootScope.Scope} $scope
 * @param {recentSongsStore} recentSongsStore
 */
function historyCtrl ($scope, recentSongsStore) {
  "ngInject";

  /**
   * fetch content from my history store
   * this is not angular event so we need to digest scope manually
   */
  (async () => {
    this.content = await recentSongsStore.fetch();
    this.display = true;
    $scope.$evalAsync();
  })();
}

export default historyCtrl;
