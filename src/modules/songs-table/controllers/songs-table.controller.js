/**
 * Songs table directive controller
 * @author anram88
 */

export default class songsTableCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {$log} $log
   * @param {myLibraryStore} myLibraryStore
   */
  constructor($log, myLibraryStore, $scope) {
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
    this.$scope = $scope;
  }

  //TODO refactor this into its own model
  /**
   * Adds selected song to library
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

