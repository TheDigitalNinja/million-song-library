/**
 * login store service
 * @name loginStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {LoginSuccessResponseEntity} LoginSuccessResponseEntity
 * @returns {*}
 */
function loginStore (request, entityMapper, LoginSuccessResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/loginedge/login';
  return {
    /**
     * make login request
     * @name loginStore#push
     * @param {string} email
     * @param {string} password
     * @return {LoginSuccessResponseEntity}
     */
    async push(email, password) {
      const data = { email, password };
      const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };

      const response = await request.post(API_REQUEST_PATH, data, headers);
      return entityMapper(response, LoginSuccessResponseEntity);
    },
  };
}

export default loginStore;
