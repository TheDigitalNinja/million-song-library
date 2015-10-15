import SessionInfoEntity from '../entities/SessionInfoEntity';

/**
 * session info store service
 * @name sessionInfoStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function sessionInfoStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/loginedge/sessioninfo/';
  return {
    /**
     * fetch session data by session id
     * @name sessionInfoStore#fetch
     * @param {string} sessionId
     * @return {SongInfoEntity}
     */
    async fetch(sessionId) {
      return entityMapper(await request.get(API_REQUEST_PATH + sessionId), SessionInfoEntity);
    },
  };
}

export default sessionInfoStore;
