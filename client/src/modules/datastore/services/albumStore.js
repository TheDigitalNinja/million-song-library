/**
 * album store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {AlbumInfoEntity} AlbumInfoEntity
 * @param {AlbumListEntity} AlbumListEntity
 * @param {$log} $log
 * @returns {*}
 */
function albumStore(request, entityMapper, AlbumInfoEntity, AlbumListEntity, $log) {
  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/catalogedge/';
  return {
    /**
     * fetch album from catalogue endpoint
     * @name albumStore#fetch
     * @param {string} albumId
     * @return {AlbumInfoEntity}
     */
    async fetch(albumId) {
      try {
        const response = await request.get(`${ API_REQUEST_PATH }album/${ albumId }`);
        return entityMapper(response.data, AlbumInfoEntity);
      } catch(error) {
        $log.error(error);
      }
    },

    /**
     * fetch all albums from catalogue endpoint
     * @name albumStore#fetchAll
     * @param {string} facets - comma separated list of facetIds
     * @return {AlbumListEntity}
     */
    async fetchAll(facets) {
      try {
        const params = { params: { facets: facets } };
        const response = await request.get(`${ API_REQUEST_PATH }browse/album`, params);
        return entityMapper(response.data, AlbumListEntity);
      } catch(error) {
        $log.error(error);
      }
    },
  };
}

export default albumStore;
