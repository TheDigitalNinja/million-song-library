import _ from 'lodash';

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

    this.songsPerSlide = 2;

    this.getLibrarySongs();
    this.artistSlides = [];
    this.albumSlides = [];
  }

  getLibrarySongs() {
    (async () => {
      try {
        const response = await this.myLibraryStore.fetch();
        this.songs = response.songs;
        this.songSlides = _.chunk(this.songs, this.songsPerSlide);
        this.$scope.$evalAsync();
      }
      catch(error) {
        this.$log.warn(error);
      }
    })();
  }
}
