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
   * @param {string} songId
   */
  addSongToLibrary(songId) {
    this.myLibraryStore.addSong(songId);
  }

  /**
   * Adds selected album into library
   * @param {string} albumId
   */
  addAlbumToLibrary(albumId) {
    this.myLibraryStore.addAlbum(albumId);
  }

  /**
   * Adds selected artist into library
   * @param {string} artistId
   */
  addArtistToLibrary(artistId) {
    this.myLibraryStore.addArtist(artistId);
  }

}
