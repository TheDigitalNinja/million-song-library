/**
 * Rating Model
 */
class ratingModel {
  /*@ngInject*/

  constructor(rateSongStore, rateAlbumStore, rateArtistStore) {
    this.rateSongStore = rateSongStore;
    this.rateAlbumStore = rateAlbumStore;
    this.rateArtistStore = rateArtistStore;
  }

  /**
   * rates an entity
   * @name ratingModel#rate
   * @param {string} entityId
   * @param {string} entityType
   * @param {number} rating
   */
  async rate(entityId, entityType, rating) {
    switch(entityType) {
      case 'song':
        await this._rateSong(entityId, rating);
        break;
      case 'album':
        await this._rateAlbum(entityId, rating);
        break;
      case 'artist':
        await this._rateArtist(entityId, rating);
        break;
    }
  }

  /**
   * Push to the album store the new rating
   * @param {string} albumId
   * @param {number} rating
   * @private
   */
  async _rateAlbum(albumId, rating) {
    await this.rateAlbumStore.push(albumId, rating);
  }

  /**
   * Push to the song store the new rating
   * @param {string} songId
   * @param {number} rating
   * @private
   */
  async _rateSong(songId, rating) {
    await this.rateSongStore.push(songId, rating);
  }

  /**
   * Push to the artist store the new rating
   * @param {string} artistId
   * @param {number} rating
   * @private
   */
  async _rateArtist(artistId, rating) {
    await this.rateArtistStore.push(artistId, rating);
  }
}

export default ratingModel;
