/**
 * Artist model
 * @author anram88
 * @param {albumStore} albumStore
 * @param {artistStore} artistStore
 * @param {songStore} songStore
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {{getArtist: getArtist, getArtists: getArtists, getSimilarArtists: getSimilarArtists, artist: null,
 *     artists: null}}
 */
export default function artistModel(albumStore, artistStore, songStore, $log, $rootScope) {

  let _model = {
    getArtist: getArtist,
    getArtists: getArtists,
    getArtistAlbums: getArtistAlbums,
    getSimilarArtists: getSimilarArtists,
    getArtistsById: getArtistsById,
    filterArtists: filterArtists,
    artist: null,
    artists: null,
  };
  return _model;

  /**
   * Gets songs, albums and artists info of a specific artists
   * @param {int} artistId
   * @param {function} done
   */
  async function getArtist(artistId, done) {
    try {
      _model.artist = {
        artistInfo: null,
        artistSongs: [],
        artistAlbums: [],
      };
      _model.artist.artistInfo = await artistStore.fetch(artistId);
      _model.artist.artistSongs = await songStore.fetchAll({ artist: artistId });
      // TODO: get albums from artist info

      if(done) {
        done(_model.artist);
      }

      $rootScope.$new().$evalAsync();
    } catch(err) {
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
    catch(err) {
      _model.artists = [];
      $log.warn(err);
    }
  }

  /**
   * Gets a list of albums
   * @param {string[]} albumIds
   * @param {function} done
   */
  async function getArtistAlbums(albumIds, done) {
    try {
      const albums = albumIds.map(async (albumId) => await albumStore.fetch(albumId));
      _model.albums = await* albums;

      if(done) {
        done(_model.albums);
      }

      $rootScope.$new().$evalAsync();
    } catch(err) {
      _model.albums = [];
      $log.warn(err);
    }
  }

  /**
   * Gets a list of similar artists of the received artist
   * @param {string} artistId
   * @param {function} done
   */
  async function getSimilarArtists(artistId, done) {
    try {
      const artist = await artistStore.fetch(artistId);
      await _model.getArtistsById(artist.similarArtistsList);
      if(done) {
        done(_model.artists);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }

  /**
   * Gets a list of artists
   * @param {string[]} artistIds
   * @param {function} done
   */
  async function getArtistsById(artistIds, done) {
    try {
      const artists = artistIds.map(async (artistId) => await artistStore.fetch(artistId));
      _model.artists = await* artists;

      if(done) {
        done(_model.artists);
      }

      $rootScope.$new().$evalAsync();
    } catch(err) {
      _model.artists = [];
      $log.warn(err);
    }
  }

  /**
   * Gets a list of artists filtered by rating and genre
   * @param {number} rating
   * @param {string} genre
   * @param {function} done
   */
  async function filterArtists(rating, genre, done) {
    try {
      const artistList = await artistStore.fetchAll(genre);
      if(done) {
        done(artistList.artists);
      }
    }
    catch(error) {
      $log.warn(error);
    }
  }
}
