/**
 * Artist model
 * @author anram88
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$log} $log
 * @returns {{getArtist: getArtist, getArtists: getArtists, getSimilarArtists: getSimilarArtists, artist: null,
 *     artists: null}}
 */
export default function artistModel(artistStore, catalogStore, $log) {

  let _model = {
    getArtist: getArtist,
    getArtists: getArtists,
    getSimilarArtists: getSimilarArtists,
    artist: null,
    artists: null,
  };
  return _model;

  /**
   * Gets songs, albums and artists info of a specific artists
   * @param {$scope} $scope
   * @param {int} artistId
   */
  function getArtist($scope, artistId) {
    (async() => {
      try {
        _model.artist = {
          artistInfo: null,
          artistSongs: [],
          artistAlbums: [],
        };
        _model.artist.artistInfo = await artistStore.fetch(artistId);
        _model.artist.artistSongs = await catalogStore.fetch({ artist: artistId });
        _model.artist.artistAlbums = await artistStore.fetchArtistAlbums(artistId);

        $scope.$evalAsync();
      } catch (err) {
        $log.warn(err);
      }
    })();
  }

  /**
   * Gets a list of all artists
   * @param {$scope} $scope
   */
  function getArtists($scope) {
    (async () => {
      try {
        const artistList = await artistStore.fetchAll();
        _model.artists = artistList.artists;
        $scope.$evalAsync();
      }
      catch (err) {
        _model.artists = [];
        $log.warn(err);
      }
    })();
  }

  /**
   * Gets a list of similar artists
   * @param {$scope} $scope
   * @param {int} artistId
   */
  function getSimilarArtists($scope, artistId) {
    (async() => {
      try {
        const artistList = await artistStore.fetchSimilarArtist(artistId);
        _model.artists = artistList.artists;
        $scope.$evalAsync();
      } catch (err) {
        _model.artists = [];
        $log.warn(err);
      }
    })();
  }
}
