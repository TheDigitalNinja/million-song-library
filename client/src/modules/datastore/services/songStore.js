/**
 * songs store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SongInfoEntity} SongInfoEntity
 * @returns {*}
 */
function songStore(request, entityMapper, SongInfoEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/catalogedge/song/';
  return {
    /**
     * fetch songs from catalogue endpoint
     * @name songStore#fetch
     * @param {string} songId
     * @return {SongInfoEntity}
     */
    async fetch(songId) {
      const response = await request.get(API_REQUEST_PATH + songId);
      return entityMapper(response, SongInfoEntity);
    },
  };
}

export default songStore;
