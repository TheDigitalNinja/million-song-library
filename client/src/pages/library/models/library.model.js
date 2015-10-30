/**
 * Library model
 * @author anram88
 * @param {$log} $log
 * @returns {{addSongToLibrary: addSongToLibrary, addAlbumToLibrary: addAlbumToLibrary, addArtistToLibrary:
 *     addArtistToLibrary}}
 */
export default function libraryModel($log) {

  // TODO: Use myLibraryStore methods to add to the library once they are defined
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
    $log.info(`Adding song ${songId} to library`);
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
