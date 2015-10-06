import ArtistInfoEntity from '../entities/ArtistInfoEntity';
import ArtistListEntity from '../entities/ArtistListEntity';
import AlbumListEntity from '../entities/AlbumListEntity';

/**
 * artist store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
export default function artistStore(request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/catalogedge/artists/';
  return {
    /**
     * fetch artist from catalogue endpoint
     * @name artistStore#fetch
     * @param {string} artistId
     * @return {ArtistInfoEntity}
     */
    async fetch(artistId) {
      return entityMapper(await request.get(API_REQUEST_PATH + artistId), ArtistInfoEntity);
    },

    /**
     * fetch all artists from catalogue endpoint
     * @name artistStore#fetchAll
     * @param {string} genre
     * @return {ArtistListEntity}
     */
    async fetchAll(genre) {
      return entityMapper(await request.get(
          API_REQUEST_PATH, { params: { genreName: genre } }),
        ArtistListEntity
      );
    },

    /**
     * fetch all artist's albums from catalogue endpoint
     * @name artistStore#fetchArtistsAlbums
     * @param {string} artistId
     * @return {AlbumListEntity}
     */
    async fetchArtistAlbums(artistId) {
      return entityMapper(await request.get(
          `${API_REQUEST_PATH}${artistId}/albums`),
        AlbumListEntity
      );
    },
    async fetchSimilarArtist(artistId) {
      return entityMapper(await request.get(
          `${API_REQUEST_PATH}${artistId}/similar-artists`),
        ArtistListEntity);
    },
  };
}
