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
    this.$log = $log;
    this.libraryModel = libraryModel;
    this.toastr = toastr;
    this.isProcessing = true;
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
      const response = await this.libraryModel.getLibrary();
      if(response) {
        this.songs = response.songs;
        this.albums = response.albums;
        this.artists = response.artists;
        this.isProcessing = false;
        return;
      }
      else {
        //TODO add 'no library message'
      }
    }
    catch(error) {
      this.$log.warn(error);
    }
    this.isProcessing = false;
  }
}
