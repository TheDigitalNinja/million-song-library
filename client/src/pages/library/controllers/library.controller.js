/**
 * Library controller
 */
export default class libraryCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$rootScope} $rootScope
   * @param {libraryModel} libraryModel
   * @param {toastr} toastr
   * @param {$log} $log
   */
  constructor($rootScope, libraryModel, toastr, $log) {
    this.$rootScope = $rootScope;
    this.libraryModel = libraryModel;
    this.toastr = toastr;
    this.$log = $log;
    this._getMyLibrary();

    $rootScope.$on('deletedFromLibrary', (event, data) => {
      this._getMyLibrary(data);
    });
  }

  /**
   * Initializes songs albums and artists library data
   * If no data is found (because of error on request or empty data)
   * error message is returned
   * @private
   */
  async _getMyLibrary() {
    try {
      this.isProcessing = true;
      const response = await this.libraryModel.getLibrary();
      if(response) {
        this.songs = response.songs;
        this.albums = response.albums;
        this.artists = response.artists;
        this.$rootScope.$evalAsync();
      }
      else {
        this.$log.error('No response from _getMyLibrary');
      }
    }
    catch(error) {
      this.$log.warn(error);
    }
    finally {
      this.isProcessing = false;
    }
  }
}
