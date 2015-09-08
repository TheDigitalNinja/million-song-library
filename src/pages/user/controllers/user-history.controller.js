/**
 * my page history state controller
 * @param {$rootScope.Scope} $scope
 * @param {recentSongsStore} recentSongsStore
 */
export default class historyCtrl {
  /*@ngInject*/

  constructor($scope, recentSongsStore) {
    var vm = this;
    /**
     * fetch content from my history store
     * this is not angular event so we need to digest scope manually
     */
    (async () => {
      vm.content = await recentSongsStore.fetch();
      vm.display = true;
      $scope.$evalAsync();
    })();
  }

}

