function libraryCtrl ($scope, myLibraryStore) {
  "ngInject";

  // fetch content from my library store
  (async () => {
    this.content = await myLibraryStore.fetch();
    this.display = true;
    $scope.$evalAsync();
  })();

  /**
   * play button action
   * @param {string} songId
   */
  this.play = function (songId) {
    console.log(songId);
  };
}

export default libraryCtrl;
