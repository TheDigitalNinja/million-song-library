/**
 * Artist model
 * @author anram88
 * @param {artistStore} artistStore
 * @param {catalogStore} catalogStore
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {{getArtist: getArtist, getArtists: getArtists, getSimilarArtists: getSimilarArtists, artist: null,
 *     artists: null}}
 */
export default function artistModel(artistStore, catalogStore, $log, $rootScope) {

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
   * @param {int} artistId
   */
  async function getArtist(artistId) {
    try {
      _model.artist = {
        artistInfo: null,
        artistSongs: [],
        artistAlbums: [],
      };
      _model.artist.artistInfo = await artistStore.fetch(artistId);
      _model.artist.artistSongs = await catalogStore.fetch({ artist: artistId });
      _model.artist.artistAlbums = await artistStore.fetchArtistAlbums(artistId);

      $rootScope.$new().$evalAsync();
    } catch (err) {
      $log.warn(err);
    }
  }

  /**
   * Gets a list of all artists
   */
  async function getArtists() {
    try {
      const artistList = await artistStore.fetchAll();
      _model.artists = artistList.artists;
      $rootScope.$new().$evalAsync();
    }
    catch (err) {
      _model.artists = [];
      $log.warn(err);
    }
  }

  /**
   * Gets a list of similar artists
   * @param {int} artistId
   */
  async function getSimilarArtists(artistId) {
    try {
      const artistList = await artistStore.fetchSimilarArtist(artistId);
      _model.artists = artistList.artists;
      $rootScope.$new().$evalAsync();
    } catch (err) {
      _model.artists = [];
      $log.warn(err);
    }
  }
}
