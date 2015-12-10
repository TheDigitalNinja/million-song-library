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
    getArtist,
    getArtists,
    getArtistAlbums,
    getArtistSongs,
    getSimilarArtists,
    getArtistsById,
    filterArtists,
    artists: null,
    isProcessing: false,
  };
  return _model;

  /**
   * Gets songs, albums and artists info of a specific artists
   * @param {int} artistId
   * @param {function} done
   */
  async function getArtist(artistId, done) {
    _model.isProcessing = true;
    try {
      const artist = await artistStore.fetch(artistId);
      if(done) {
        done(artist);
      }
    } catch(err) {
      $log.warn(err);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of all artists
   */
  async function getArtists() {
    _model.isProcessing = true;
    try {
      const artistList = await artistStore.fetchAll();
      _model.artists = artistList.artists;
      $rootScope.$new().$evalAsync();
    }
    catch(err) {
      _model.artists = [];
      $log.warn(err);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of albums
   * @param {string[]} albumIds
   * @param {function} done
   */
  async function getArtistAlbums(albumIds, done) {
    _model.isProcessing = true;
    try {
      const albumsPromises = albumIds.map(async (albumId) => await albumStore.fetch(albumId));
      const albums = await* albumsPromises;
      if(done) {
        done(albums);
      }
    } catch(err) {
      $log.warn(err);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of similar artists of the received artist
   * @param {string} artistId
   * @param {function} done
   */
  async function getSimilarArtists(artistId, done) {
    _model.isProcessing = true;
    try {
      const artist = await artistStore.fetch(artistId);
      await _model.getArtistsById(artist.similarArtistsList, (artists) => {
        if(done) {
          done(artists);
        }
      });
    }
    catch(error) {
      $log.warn(error);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of artists
   * @param {string[]} artistIds
   * @param {function} done
   */
  async function getArtistsById(artistIds, done) {
    _model.isProcessing = true;
    try {
      const artistsPromises = artistIds.map(async (artistId) => await artistStore.fetch(artistId));
      const artists = await* artistsPromises;
      if(done) {
        done(artists);
      }
    } catch(err) {
      $log.warn(err);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of artists filtered by rating and genre
   * @param {string} facets
   * @param {function} done
   */
  async function filterArtists(facets, done) {
    _model.isProcessing = true;
    try {
      const artistList = await artistStore.fetchAll(facets);
      if(done) {
        done(artistList.artists);
      }
    }
    catch(error) {
      $log.warn(error);
    }
    _model.isProcessing = false;
  }

  /**
   * Gets a list of songs from the artist songList
   * @param {ArtistInfoEntity} artist
   * @param {function} done
   */
  async function getArtistSongs(artist, done) {
    _model.isProcessing = true;
    try {
      const songsList = artist.songsList;
      const songPromises = songsList.map(async (songId) => await songStore.fetch(songId));
      const songs = await* songPromises;
      if(done) {
        done(songs);
      }
    }
    catch(error) {
      $log.warn(error);
    }
    _model.isProcessing = false;
  }
}
