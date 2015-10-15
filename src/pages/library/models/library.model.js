/**
 * Library model
 * @author anram88
 * @param {$log} $log
 * @param {myLibraryStore} myLibraryStore
 * @param {$rootScope} $rootScope
 * @returns {{addSongToLibrary: addSongToLibrary, addAlbumToLibrary: addAlbumToLibrary, addArtistToLibrary:
 *     addArtistToLibrary}}
 */
export default function libraryModel($log, myLibraryStore, $rootScope) {

  const _model = {
    addSongToLibrary: addSongToLibrary,
    addAlbumToLibrary: addAlbumToLibrary,
    addArtistToLibrary: addArtistToLibrary,
  };
  return _model;

  /**
   * Adds selected song to library
   * @param {int} songId
   */
  async function addSongToLibrary(songId) {
    try {
      await myLibraryStore.addSong(songId);
      $rootScope.$new().$evalAsync();
    }
    catch (error) {
      $log.warn(error);
    }
  }

  /**
   * Adds selected album into library
   * @param {int} albumId
   */
  function addAlbumToLibrary(albumId) {
    $log.info(`Adding album ${albumId} to library`);
  }

  /**
   * Adds selected artist into library
   * @param {int} artistId
   */
  function addArtistToLibrary(artistId) {
    $log.info(`Adding artist ${artistId} to library`);
  }

}
