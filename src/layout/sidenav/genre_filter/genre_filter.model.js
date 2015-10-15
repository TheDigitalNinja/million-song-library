/**
 * Genre Filter Model
 * @param {albumStore} albumStore
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {{getSongsFilteredByGenre: getSongsFilteredByGenre, getArtistsFilteredByGenre: getArtistsFilteredByGenre,
 *     getAlbumsFilteredByGenre: getAlbumsFilteredByGenre, selectedGenre: null, songs: null, artists: null, albums:
 *     null}}
 */

export default function genreFilterModel(albumStore,
                                         artistStore,
                                         catalogStore,
                                         $log,
                                         $rootScope) {

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
   */
  async function getArtistsFilteredByGenre() {
    try {
      const artistList = await artistStore.fetchAll(_model.selectedGenre);
      _model.artists = artistList.artists;
      $rootScope.$new().$evalAsync();
    }
    catch (err) {
      $log.warn(err);
    }
  }

  /**
   * Filters albums by genre
   */
  async function getAlbumsFilteredByGenre() {
    try {
      const albumList = await albumStore.fetchAll(_model.selectedGenre);
      _model.albums = albumList.albums;
      $rootScope.$new().$evalAsync();
    }
    catch (error) {
      $log.warn(error);
    }
  }

  /**
   * Filters songs by genre
   */
  async function getSongsFilteredByGenre() {
    try {
      const songsList = await catalogStore.fetch({
        genre: _model.selectedGenre,
        rating: null,
        artist: null,
      });
      _model.songs = songsList.songs;
      $rootScope.$new().$evalAsync();
    }
    catch (error) {
      $log.warn(error);
    }
  }

}
