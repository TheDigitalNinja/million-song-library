/**
 * Song model
 * @param {$log} $log
 * @param {songStore} songStore
 * @param {$rootScope} $rootScope
 * @returns {{getSong: getSong, getSongs: getSongs, song: null, songs: null}}
 */
export default function songModel($log, songStore, $rootScope) {
  let _model = {
    getSong: getSong,
    getSongs: getSongs,
    filterSongs: filterSongs,
    song: null,
    songs: null,
  };
  return _model;

  /**
   * Retrieves information of a single song
   * @param {int} songId
   * @param {function} done
   */
  async function getSong(songId, done) {
    try {
      const song = await songStore.fetch(songId);
      if(done) {
        done(song);
      }
    }
    catch(err) {
      $log.warn(err);
    }
  }

  /**
   * Gets all songs
   */
  async function getSongs() {
    try {
      const songList = await songStore.fetchAll();
      _model.songs = songList.songs;
      $rootScope.$new().$evalAsync();
    }
    catch(error) {
      $log.warn(error);
    }
  }

  /**
   * Gets songs filtered by rating and genre
   * @param {integer} rating
   * @param {string} genre
   * @param {function} done
   */
  async function filterSongs(rating, genre, done) {
    try {
      const songsList = await songStore.fetchAll({ rating, genre });
      const songs = songsList.songs;
      if(done) {
        done(songs);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }

}
