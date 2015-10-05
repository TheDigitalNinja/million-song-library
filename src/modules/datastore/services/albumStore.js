import AlbumListEntity from '../entities/AlbumListEntity';
import AlbumInfoEntity from '../entities/AlbumInfoEntity';
/**
 * album store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
export default function albumStore(request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/catalogedge/albums/';
  return {
    /**
     * fetch album from catalogue endpoint
     * @name albumStore#fetch
     * @param {string} albumId
     * @return {AlbumInfoEntity}
     */
    async fetch(albumId) {
      return entityMapper(await request.get(
          `${API_REQUEST_PATH}${albumId}`),
        AlbumInfoEntity);
    },

    /**
     * fetch all albums from catalogue endpoint
     * @name albumStore#fetchAll
     * @param {string} genre
     * @return {AlbumListEntity}
     */
    async fetchAll (genre) {
      return entityMapper(await request.get(
          API_REQUEST_PATH, { params: { genreName: genre } }),
        AlbumListEntity);
    },
  };
}
