/**
 * Library controller
 */
export default class libraryCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$scope} $scope
   * @param {$log} $log
   * @param {myLibraryStore} myLibraryStore
   */
  constructor($scope, $log, myLibraryStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;

    this._getMyLibrary();
    this.artistSlides = [];
  }

  /**
   * Gets songs, albums and artists into library
   * @private
   */
  async _getMyLibrary() {
    try {
      const response = await this.myLibraryStore.fetch();
      this.songs = response.songs;
      this.albums = response.albums;
      this.$scope.$evalAsync();
    }
    catch(error) {
      this.$log.warn(error);
    }
  }
}
