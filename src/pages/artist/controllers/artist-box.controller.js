/**
 * Artist box directive controller
 * @author anram88
 */

export default class artistBoxCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {$log} $log
   */
  constructor($log) {
    this.$log = $log;
  }

  /**
   * Adds artists to library
   * @param {int} artistId
   */
  addToMyLibrary(artistId) {
    /*TODO replace for actual method call*/
    this.$log.info('Adding ${artistId} to my library');
  }

}

