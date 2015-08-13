function libraryCtrl ($scope, myLibraryStore) {
  "ngInject";

  // fetch content from my library store
  (async () => {
    this.content = await myLibraryStore.fetch();
    this.display = true;
    $scope.$evalAsync();
  })();
}

export default libraryCtrl;
