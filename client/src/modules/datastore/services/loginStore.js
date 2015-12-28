import {LOGIN_EDGE} from '../../../constants.js';

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

  const API_REQUEST_PATH = `${LOGIN_EDGE}login`;
  return {
    /**
     * make login request
     * @name loginStore#push
     * @param {string} email
     * @param {string} password
     * @return {LoginSuccessResponseEntity}
     */
    async push(email, password) {
      const data = `email=${ email }&password=${ password }`;
      const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };

      const response = await request.post(API_REQUEST_PATH, data, headers);
      return entityMapper(response.data, LoginSuccessResponseEntity);
    },
  };
}

export default loginStore;
