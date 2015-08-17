/**
 * my page library sate controller
 * @param {$rootScope.Scope} $scope
 * @param {myLibraryStore} myLibraryStore
 */
function libraryCtrl ($scope, myLibraryStore) {
  "ngInject";

  /**
   * fetch content from my library store
   * this is not angular event to we need to digest scope manually
   */
  (async () => {
    this.content = await myLibraryStore.fetch();
    this.display = true;
    $scope.$evalAsync();
  })();
}

export default libraryCtrl;
