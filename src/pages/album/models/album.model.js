/**
 * Album Model
 * @author anram88
 * @param {albumStore} albumStore
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {{getAlbum: getAlbum, getAlbums: getAlbums, album: null, albums: null}}
 */
export default function albumModel(albumStore, $log, $rootScope) {

  let _model = {
    getAlbum: getAlbum,
    getAlbums: getAlbums,
    filterAlbums: filterAlbums,
    album: null,
    albums: null,
  };
  return _model;

  /**
   * Gets a specific album
   * @param {int} albumId
   */
  async function getAlbum(albumId) {
    try {
      _model.album = await albumStore.fetch(albumId);
      $rootScope.$new().$evalAsync();
    } catch (err) {
      $log.warn(err);
    }
  }

  /**
   * Gets all albums
   */
  async function getAlbums() {
    try {
      const albumList = await albumStore.fetchAll();
      _model.albums = albumList.albums;
      $rootScope.$new().$evalAsync();
    }
    catch (error) {
      $log.warn(error);
    }
  }

  /**
   * Gets a list of albums filtered by rating and genre
   * @param {number} rating
   * @param {string} genre
   * @param {function} callback
   */
  async function filterAlbums(rating, genre, callback) {
    try {
      const albumList = await albumStore.fetchAll(genre);
      if(callback) {
        callback(albumList.albums);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }
}
