/**
 * Library model
 */
export default class libraryModel {

  /**
   * @constructor
   * @param {$rootScope} $rootScope
   * @param {myLibraryStore} myLibraryStore
   * @param {toastr} toastr
   * @param {$log} $log
   */
  constructor($rootScope, myLibraryStore, toastr, $log) {
    this.$rootScope = $rootScope;
    this.myLibraryStore = myLibraryStore;
    this.toastr = toastr;
    this.$log = $log;
  }

  /**
   * Retrieves the library data (songs, artists, albums)
   */
  async getLibrary() {
    try {
      let response = await this.myLibraryStore.fetch();
      if(response) {
        return response;
      }
    } catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to retrieve library, please try again later');
    return null;
  }

  /**
   * Adds selected song to library
   * @param {string} songId
   */
  async addSongToLibrary(songId) {
    try {
      let response = await this.myLibraryStore.addSong(songId);
      if(response.message === 'success') {
        this.toastr.success('Successfully added song to library');
        this.$rootScope.$emit('addedToLibrary', 'Song');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to add song to library, please try again later');
  }

  /**
   * Adds selected album into library
   * @param {string} albumId
   */
  async addAlbumToLibrary(albumId) {
    try {
      let response = await this.myLibraryStore.addAlbum(albumId);
      if(response.message === 'success') {
        this.toastr.success('Successfully added album to library');
        this.$rootScope.$emit('addedToLibrary', 'Album');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to add album to library, please try again later');
  }

  /**
   * Adds selected artist into library
   * @param {string} artistId
   */
  async addArtistToLibrary(artistId) {
    try {
      let response = await this.myLibraryStore.addArtist(artistId);
      if(response.message === 'success') {
        this.toastr.success('Successfully added artist to library');
        this.$rootScope.$emit('addedToLibrary', 'Artist');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to add artist to library, please try again later');
  }

  /**
   * Removes selected song from library
   * @param {string} songId
   * @param {string} timestamp
   */
  async removeSongFromLibrary(songId, timestamp) {
    try {
      let response = await this.myLibraryStore.removeSong(songId, timestamp);
      if(response.message === 'success') {
        this.toastr.success('Successfully removed song');
        this.$rootScope.$emit('deletedFromLibrary', 'Song');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to delete song, please try again later');
  }

  /**
   * Removes selected album from library
   * @param {string} albumId
   * @param {string} timestamp
   */
  async removeAlbumFromLibrary(albumId, timestamp) {
    try {
      let response = await this.myLibraryStore.removeAlbum(albumId, timestamp);
      if(response.message === 'success') {
        this.toastr.success('Successfully removed album');
        this.$rootScope.$emit('deletedFromLibrary', 'Album');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to delete album, please try again later');
  }

  /**
   * Removes selected artist from library
   * @param {string} artistId
   * @param {string} timestamp
   */
  async removeArtistFromLibrary(artistId, timestamp) {
    try {
      let response = await this.myLibraryStore.removeArtist(artistId, timestamp);
      if(response.message === 'success') {
        this.toastr.success('Successfully removed artist');
        this.$rootScope.$emit('deletedFromLibrary', 'Artist');
        return;
      }
    }
    catch(err) {
      this.$log.error(err);
    }
    this.toastr.error('Unable to delete artist, please try again later');
  }

}
