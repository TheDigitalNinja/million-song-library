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
   * @param {$scope} $scope
   */
  constructor($log, myLibraryStore, $scope) {
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
    this.$scope = $scope;
  }

}

