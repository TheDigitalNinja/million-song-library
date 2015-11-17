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

    this.getLibrarySongs();
    this.artistSlides = [];
    this.albumSlides = [];
  }

  async getLibrarySongs() {
    try {
      const response = await this.myLibraryStore.fetch();
      this.songs = response.songs;
      this.$scope.$evalAsync();
    }
    catch(error) {
      this.$log.warn(error);
    }
  }
}
