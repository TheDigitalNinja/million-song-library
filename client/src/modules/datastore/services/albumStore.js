/**
 * album store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {AlbumInfoEntity} AlbumInfoEntity
 * @param {AlbumListEntity} AlbumListEntity
 * @returns {*}
 */
export default function albumStore(request, entityMapper, AlbumInfoEntity, AlbumListEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/catalogedge/';
  return {
    /**
     * fetch album from catalogue endpoint
     * @name albumStore#fetch
     * @param {string} albumId
     * @return {AlbumInfoEntity}
     */
    async fetch(albumId) {
      const response = await request.get(`${API_REQUEST_PATH}album/${albumId}`);
      return entityMapper(response, AlbumInfoEntity);
    },

    /**
     * fetch all albums from catalogue endpoint
     * @name albumStore#fetchAll
     * @param {string} genre
     * @return {AlbumListEntity}
     */
    async fetchAll (genre) {
      const params = { params: { facets: genre } };
      const response = await request.get(`${ API_REQUEST_PATH }browse/album`, params);
      return entityMapper(response, AlbumListEntity);
    },
  };
}
