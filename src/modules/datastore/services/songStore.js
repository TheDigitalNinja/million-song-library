import SongInfoEntity from '../entities/SongInfoEntity';

/**
 * songs store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function songStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/catalogedge/song/';
  return {
    /**
     * fetch songs from catalogue endpoint
     * @name songStore#fetch
     * @param {string} songId
     * @return {SongInfoEntity}
     */
    async fetch(songId) {
      return entityMapper(await request.get(API_REQUEST_PATH + songId), SongInfoEntity);
    }
  };
}

export default songStore;
