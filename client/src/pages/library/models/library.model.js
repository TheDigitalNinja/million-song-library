/**
 * Library model
 * @param {$log} $log
 * @param {myLibraryStore} myLibraryStore
 */
export default class libraryModel {

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
  async addSongToLibrary(songId) {
    await this.myLibraryStore.addSong(songId);
  }

  /**
   * Adds selected album into library
   * @param {string} albumId
   */
  async addAlbumToLibrary(albumId) {
    await this.myLibraryStore.addAlbum(albumId);
  }

  /**
   * Adds selected artist into library
   * @param {string} artistId
   */
  async addArtistToLibrary(artistId) {
    await this.myLibraryStore.addArtist(artistId);
  }

  /**
   * Removes selected song from library
   * @param {string} songId
   * @param {string} timestamp
   */
  removeSongFromLibrary(songId, timestamp) {
    this.myLibraryStore.removeSong(songId, timestamp);
  }

  /**
   * Removes selected album from library
   * @param {string} albumId
   * @param {string} timestamp
   */
  removeAlbumFromLibrary(albumId, timestamp) {
    this.myLibraryStore.removeAlbum(albumId, timestamp);
  }

  /**
   * Removes selected artist from library
   * @param {string} artistId
   * @param {string} timestamp
   */
  removeArtistFromLibrary(artistId, timestamp) {
    this.myLibraryStore.removeArtist(artistId, timestamp);
  }

}
