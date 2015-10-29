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

  const API_REQUEST_PATH = '/api/v1/loginedge/logout';
  return {
    /**
     * make logout request
     * @name logoutStore#push
     * @return {StatusResponseEntity}
     */
    async push() {
      const response = await request.post(API_REQUEST_PATH);
      return entityMapper(response, StatusResponseEntity);
    },
  };
}

export default logoutStore;
