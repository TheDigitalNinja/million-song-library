/**
 * songs store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SongInfoEntity} SongInfoEntity
 * @param {SongListEntity} SongListEntity
 * @returns {*}
 */
function songStore(request, entityMapper, SongInfoEntity, SongListEntity) {
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
      const response = await request.get(`${ API_REQUEST_PATH }song/${ songId }`);
      return entityMapper(response, SongInfoEntity);
    },

    /**
     * fetch songs from catalogue endpoint
     * @name songStore#fetchAll
     * @param {array} opts
     * @return {SongListEntity}
     */
    async fetchAll(opts) {
      const params = { params: { facets: JSON.stringify(opts) } };
      const response = await request.get(`${ API_REQUEST_PATH }browse/song`, params);
      return entityMapper(response, SongListEntity);
    },
  };
}

export default songStore;
