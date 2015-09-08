/**
 * my page library state controller
 * @param {$rootScope.Scope} $scope
 * @param {myLibraryStore} myLibraryStore
 */
export default class libraryCtrl {

  /*@ngInject*/

  constructor($scope, myLibraryStore) {
    var vm = this;
    /**
     * fetch content from my library store
     * this is not angular event so we need to digest scope manually
     */
    (async () => {
      vm.content = await myLibraryStore.fetch();
      vm.display = true;
      $scope.$evalAsync();
    })();
  }

}
