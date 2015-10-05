/**
 * Genre Filter Model
 * @param $log
 * @param catalogStore
 * @param artistStore
 * @param albumStore
 * @returns {{getSongsFilteredByGenre: getSongsFilteredByGenre, getArtistsFilteredByGenre: getArtistsFilteredByGenre,
 *     getAlbumsFilteredByGenre: getAlbumsFilteredByGenre, selectedGenre: null, songs: null, artists: null, albums:
 *     null}}
 */

export default function genreFilterModel($log, catalogStore, artistStore, albumStore) {

  /*@ngInject*/
  let _model = {
    getSongsFilteredByGenre: getSongsFilteredByGenre,
    getArtistsFilteredByGenre: getArtistsFilteredByGenre,
    getAlbumsFilteredByGenre: getAlbumsFilteredByGenre,
    selectedGenre: null,
    songs: null,
    artists: null,
    albums: null,
  };

  return _model;

  /**
   * Fetches all artists filtered by genre
   * @param {$scope} $scope
   */
  function getArtistsFilteredByGenre($scope) {
    (async () => {
      try {
        const artistList = await artistStore.fetchAll(_model.selectedGenre);
        _model.artists = artistList.artists;
        $scope.$evalAsync();
      }
      catch (err) {
        $log.warn(err);
      }
    })();
  }

  /**
   * Filters albums by genre
   * @param {$scope} $scope
   */
  function getAlbumsFilteredByGenre($scope) {
    (async () => {
      try {
        const albumList = await albumStore.fetchAll(_model.selectedGenre);
        _model.albums = albumList.albums;
        $scope.$evalAsync();
      }
      catch (error) {
        $log.warn(error);
      }
    })();
  }

  /**
   * Filters songs by genre
   * @param {$scope} $scope
   */
  function getSongsFilteredByGenre($scope) {
    (async () => {
      try {
        const songsList = await catalogStore.fetch({
          genre: _model.selectedGenre,
          rating: null,
          artist: null,
        });
        _model.songs = songsList.songs;
        $scope.$evalAsync();
      }
      catch (error) {
        $log.warn(error);
      }
    })();
  }

}
