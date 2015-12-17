/**
 * Library controller
 */
export default class libraryCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$scope} $scope
   * @param {$rootScope} $rootScope
   * @param {$log} $log
   * @param {myLibraryStore} myLibraryStore
   */
  constructor($scope, $rootScope, $log, myLibraryStore) {
    this.$scope = $scope;
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
    this.isProcessing = true;
    this._getMyLibrary();

    $rootScope.$on('deletedFromLibrary', (event, data) => {
      this._getMyLibrary(data);
    });
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
      this.artists = response.artists;
      this.$scope.$evalAsync();
      this.isProcessing = false;
    }
    catch(error) {
      this.$log.warn(error);
    }
  }
}
