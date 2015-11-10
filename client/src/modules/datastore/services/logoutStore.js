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

  const API_REQUEST_PATH = '/msl/v1/loginedge/logout';
  return {
    /**
     * make logout request
     * @name logoutStore#push
     * @return {StatusResponseEntity}
     */
    async push() {

      const headers = { headers: { 'Content-Type': 'application/json' } };

      const response = await request.post(API_REQUEST_PATH, null, headers);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default logoutStore;
