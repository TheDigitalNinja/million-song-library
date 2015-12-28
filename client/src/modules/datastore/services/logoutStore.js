import {LOGIN_EDGE} from '../../../constants.js';

/**
 * logout store service
 * @name logoutStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {StatusResponseEntity} StatusResponseEntity
 * @returns {*}
 */
function logoutStore (request, entityMapper, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = `${LOGIN_EDGE}logout`;
  return {
    /**
     * make logout request
     * @name logoutStore#push
     * @return {StatusResponseEntity}
     */
    async push() {
      const response = await request.post(API_REQUEST_PATH, null);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default logoutStore;
