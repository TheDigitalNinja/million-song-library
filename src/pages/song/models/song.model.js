/**
 * Song model
 * @author anram88
 * @param {catalogStore} catalogStore
 * @param {$log} $log
 * @param {songStore} songStore
 * @returns {{getSong: getSong, getSongs: getSongs, song: null, songs: null}}
 */
export default function songModel(catalogStore, $log, songStore) {
  let _model = {
    getSong: getSong,
    getSongs: getSongs,
    song: null,
    songs: null,
  };
  return _model;

  /**
   * Retrieves information of a single song
   * @param {$scope} $scope
   * @param {int} songId
   */
  function getSong($scope, songId) {
    (async() => {
      try {
        _model.song = await songStore.fetch(songId);
        $scope.$evalAsync();
      } catch (err) {
        $log.warn(err);
      }
    })();
  }

  /**
   * Gets all songs
   * @param {$scope} $scope
   */
  function getSongs($scope) {
    (async () => {
      try {
        const songList = await catalogStore.fetch();
        _model.songs = songList.songs;
        $scope.$evalAsync();
      }
      catch (error) {
        $log.warn(error);
      }
    })();
  }
}
