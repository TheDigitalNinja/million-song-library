/**
 * Rating Model
 */
class ratingModel {
  /*@ngInject*/

  constructor(rateSongStore, rateAlbumStore) {
    this.rateSongStore = rateSongStore;
    this.rateAlbumStore = rateAlbumStore;
  }

  /**
   * rates an entity
   * @name ratingModel#rate
   * @param {string} entityId
   * @param {string} entityType
   * @param {number} rating
   */
  rate(entityId, entityType, rating) {
    switch(entityType) {
      case 'song':
        this._rateSong(entityId, rating);
        break;
      case 'album':
        this._rateAlbum(entityId, rating);
        break;
    }
  }

  /**
   * Push to the album store the new rating
   * @param {string} albumId
   * @param {number} rating
   * @private
   */
  _rateAlbum(albumId, rating) {
    this.rateAlbumStore.push(albumId, rating);
  }

  /**
   * Push to the song store the new rating
   * @param {string} songId
   * @param {number} rating
   * @private
   */
  _rateSong(songId, rating) {
    this.rateSongStore.push(songId, rating);
  }
}

export default ratingModel;
