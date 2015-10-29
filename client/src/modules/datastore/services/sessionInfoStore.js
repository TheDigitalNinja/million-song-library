/**
 * session info store service
 * @name sessionInfoStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SessionInfoEntity} SessionInfoEntity
 * @returns {*}
 */
function sessionInfoStore (request, entityMapper, SessionInfoEntity) {
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
      const response = await request.get(API_REQUEST_PATH);
      return entityMapper(response, SessionInfoEntity);
    },
  };
}

export default sessionInfoStore;
