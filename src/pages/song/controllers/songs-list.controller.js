/**
 * Songs list controller
 * @param {$rootScope.Scope} $scope
 * @param {$log} $log
 * @param {myLibraryStore} myLibraryStore
 */
export default class songsListCtrl {
  /*@ngInject*/

  constructor($scope, $log, myLibraryStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
  }

  addToMyLibrary(songId) {
    (async () => {
      try {
        await this.myLibraryStore.addSong(songId);
        this.$scope.$evalAsync();
      }
      catch (error) {
        this.$log.warn(error);
      }
    })();
  }
}

