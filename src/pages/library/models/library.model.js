/**
 * Library model
 * @author anram88
 * @param {$log} $log
 * @param {myLibraryStore} myLibraryStore
 * @returns {{addSongToLibrary: addSongToLibrary, addAlbumToLibrary: addAlbumToLibrary, addArtistToLibrary:
 *     addArtistToLibrary}}
 */
export default function libraryModel($log, myLibraryStore) {

  let _model = {
    addSongToLibrary: addSongToLibrary,
    addAlbumToLibrary: addAlbumToLibrary,
    addArtistToLibrary: addArtistToLibrary,
  };
  return _model;

  /**
   * Adds selected song to library
   * @param {$scope} $scope
   * @param {int} songId
   */
  function addSongToLibrary($scope, songId) {
    (async () => {
      try {
        await myLibraryStore.addSong(songId);
        $scope.$evalAsync();
      }
      catch (error) {
        $log.warn(error);
      }
    })();
  }

  /**
   * Adds selected album into library
   * @param {$scope} $scope
   * @param {int} albumId
   */
  function addAlbumToLibrary($scope, albumId) {
    $log.info(`Adding album ${albumId} to library`);
  }

  /**
   * Adds selected artist into library
   * @param {$scope} $scope
   * @param {int} artistId
   */
  function addArtistToLibrary($scope, artistId) {
    $log.info(`Adding artist ${artistId} to library`);
  }

}
