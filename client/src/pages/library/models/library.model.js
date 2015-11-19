/**
 * Library model
 * @param {$log} $log
 * @param {myLibraryStore} myLibraryStore
 */
export default class libraryModel {

  // TODO: Use myLibraryStore methods to add to the library once they are defined
  constructor($log, myLibraryStore) {
    this.$log = $log;
    this.myLibraryStore = myLibraryStore;
  }

  /**
   * Gets my library
   */
  async getLibrary() {
    return await this.myLibraryStore.fetch();
  }

  /**
   * Adds selected song to library
   * @param {int} songId
   */
  addSongToLibrary(songId) {
    this.myLibraryStore.addSong(songId);
  }

  /**
   * Adds selected album into library
   * @param {int} albumId
   */
  addAlbumToLibrary(albumId) {
    this.$log.info(`Adding album ${albumId} to library`);
  }

  /**
   * Adds selected artist into library
   * @param {int} artistId
   */
  addArtistToLibrary(artistId) {
    this.$log.info(`Adding artist ${artistId} to library`);
  }

}
