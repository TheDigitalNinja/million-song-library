/**
 * my page library state controller
 * @param {$rootScope.Scope} $scope
 * @param {myLibraryStore} myLibraryStore
 */
export default class libraryCtrl {

  /*@ngInject*/

  constructor($scope, myLibraryStore) {
    /**
     * fetch content from my library store
     * this is not angular event so we need to digest scope manually
     */
    (async () => {
      this.content = await myLibraryStore.fetch();
      this.display = true;
      $scope.$evalAsync();
    })();
  }

}
