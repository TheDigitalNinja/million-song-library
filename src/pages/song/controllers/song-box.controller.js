/**
 * Song box directive controller
 * @author anram88
 */
export default class songBoxCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {$rootScope.Scope} $scope
   * @param {$log} $log
   * @param {myLibraryStore} myLibraryStore
   */
  constructor($scope, $log, myLibraryStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
  }

  /**
   * Adds song to library
   * @param {int} songId
   */
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

