/**
 * Album Model
 * @author anram88
 * @param {albumStore} albumStore
 * @param {$log} $log
 * @returns {{getAlbum: getAlbum, getAlbums: getAlbums, album: null, albums: null}}
 */
export default function albumModel (albumStore, $log) {

  let _model = {
    getAlbum: getAlbum,
    getAlbums: getAlbums,
    album: null,
    albums: null,
  };
  return _model;

  /**
   * Gets a specific album
   * @param {$scope} $scope
   * @param {int} albumId
   */
  function getAlbum ($scope, albumId) {
    (async() => {
      try {
        _model.album = await albumStore.fetch(albumId);
        $scope.$evalAsync();
      } catch (err) {
        $log.warn(err);
      }
    })();
  }

  /**
   * Gets all albums
   * @param {$scope} $scope
   */
  function getAlbums ($scope) {
    (async () => {
      try {
        const albumList = await albumStore.fetchAll();
        _model.albums = albumList.albums;
        $scope.$evalAsync();
      }
      catch (error) {
        $log.warn(error);
      }
    })();
  }
}
