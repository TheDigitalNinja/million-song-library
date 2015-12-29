import {ACCOUNT_EDGE} from '../../../constants.js';

/**
 * registration store service
 * @name registrationStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {StatusResponseEntity} StatusResponseEntity
 * @returns {*}
 */
function registrationStore (request, entityMapper, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = `${ACCOUNT_EDGE}users/register`;
  return {
    /**
     * make registration request
     * @name registrationStore#push
     * @param {string} email
     * @param {string} password
     * @return {StatusResponseEntity}
     */
    async registration(email, password, passwordConfirmation) {
      const data = { email, password, passwordConfirmation };
      const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };

      const response = await request.post(API_REQUEST_PATH, data, headers);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default registrationStore;
