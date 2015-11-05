/**
 * Album Model
 * @author anram88
 * @param {albumStore} albumStore
 * @param {songStore} songStore
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {{getAlbum: getAlbum, getAlbums: getAlbums, album: null, albums: null}}
 */
export default function albumModel(albumStore, songStore, $log, $rootScope) {

  let _model = {
    getAlbum: getAlbum,
    getAlbums: getAlbums,
    filterAlbums: filterAlbums,
    getAlbumSongs: getAlbumSongs,
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
    } catch(err) {
      $log.warn(err);
    }
  }

  /**
   * Gets a list of songs
   * @param {string} albumId
   * @param {function} done
   */
  async function getAlbumSongs(albumId, done) {
    try {
      const album = await albumStore.fetch(albumId);
      await getSongsById(album.songsList);
      if(done) {
        done(_model.songs);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }

  /**
   * Gets a list of songs
   * @param {string[]} songIds
   */
  async function getSongsById(songIds) {
    try {
      const songs = songIds.map(async (songId) => await songStore.fetch(songId));

      _model.songs = await* songs;

      $rootScope.$new().$evalAsync();
    } catch(err) {
      _model.songs = [];
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
    catch(error) {
      $log.warn(error);
    }
  }

  /**
   * Gets a list of albums filtered by rating and genre
   * @param {number} rating
   * @param {string} genre
   * @param {function} done
   */
  async function filterAlbums(rating, genre, done) {
    try {
      const albumList = await albumStore.fetchAll(genre);
      if(done) {
        done(albumList.albums);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }
}
