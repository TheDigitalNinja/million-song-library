/**
 * songs store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SongInfoEntity} SongInfoEntity
 * @param {SongListEntity} SongListEntity
 * @param {$log} $log
 * @returns {*}
 */

export default function songStore(request,
                                  entityMapper,
                                  SongInfoEntity,
                                  SongListEntity,
                                  $log) {

  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/catalogedge/';
  return {
    /**
     * fetch songs from catalogue endpoint
     * @name songStore#fetch
     * @param {string} songId
     * @return {SongInfoEntity}
     */
    async fetch(songId) {
      try {
        const response = await request.get(`${ API_REQUEST_PATH }song/${ songId }`);
        return entityMapper(response.data, SongInfoEntity);
      } catch(error) {
        $log.error(error);
      }
    },

    /**
     * fetch songs from catalogue endpoint
     * @name songStore#fetchAll
     * @param {string} facets - comma separated list of facetIds
     * @return {SongListEntity}
     */
    async fetchAll(facets) {
      try {
        const params = { params: { facets: facets } };
        const response = await request.get(`${ API_REQUEST_PATH }browse/song`, params);
        return entityMapper(response.data, SongListEntity);
      } catch(error) {
        $log.error(error);
      }
    },
  };
}
