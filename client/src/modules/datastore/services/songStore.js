/**
 * songs store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SongInfoEntity} SongInfoEntity
 * @param {SongListEntity} SongListEntity
 * @param {$log} $log
 * @returns {*}
 */

export default function songStore(request, entityMapper, SongInfoEntity, SongListEntity, $log) {

  'ngInject';

  const API_REQUEST_PATH = '/api/v1/catalogedge/';
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
     * @param {array} opts
     * @return {SongListEntity}
     */
    async fetchAll(opts) {
      try {
        const params = { params: { facets: JSON.stringify(opts) } };
        const response = await request.get(`${ API_REQUEST_PATH }browse/song`, params);
        return entityMapper(response, SongListEntity);
      } catch(error) {
        $log.error(error);
      }
    },
  };
}
