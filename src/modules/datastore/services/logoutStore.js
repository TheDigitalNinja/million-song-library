import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * logout store service
 * @name logoutStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function logoutStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/loginedge/logout';
  return {
    /**
     * make logout request
     * @name logoutStore#push
     * @return {StatusResponseEntity}
     */
    async push() {
      return entityMapper(await request.post(API_REQUEST_PATH, {}), StatusResponseEntity);
    },
  };
}

export default logoutStore;
