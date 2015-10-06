/**
 * Album box directive controller
 * @author anram88
 */

export default class albumBoxCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {$log} $log
   */
  constructor($log) {
    this.$log = $log;
  }

  /**
   * Adds album to library
   * @param {int} albumId
   */
  addToMyLibrary(albumId) {
    /*TODO replace for actual method*/
    this.$log.info('Adding ${albumId} to library');
  }

}

