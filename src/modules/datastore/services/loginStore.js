import querystring from "querystring";
import LoginSuccessResponseEntity from "../entities/LoginSuccessResponseEntity";

/**
 * login store service
 * @name loginStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function loginStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/loginedge/login";
  return {
    /**
     * make login request
     * @name loginStore#push
     * @param {string} email
     * @param {string} password
     * @return {LoginSuccessResponseEntity}
     */
    async push(email, password) {
      var data = querystring.stringify({email, password});
      var headers = {"Content-Type": "application/x-www-form-urlencoded"};
      return entityMapper(await request.post(API_REQUEST_PATH, data, headers), LoginSuccessResponseEntity);
    }
  };
}

export default loginStore;
