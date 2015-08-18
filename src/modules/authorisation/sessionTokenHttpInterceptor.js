import url from "url";

/**
 * session token angular http interceptor
 * this adds session token header - as SessionId if
 * session token is set
 * @param {sessionToken} sessionToken
 * @returns {*}
 */
function sessionTokenHttpInterceptor (sessionToken) {
  "ngInject";

  // parse current api host - api host is set as environment
  // variable when building
  var apiUrl = url.parse(process.env.API_HOST);
  return {
    /**
     * request interceptor
     * @param {Object} config
     * @return {Object}
     */
    request(config) {
      // check if we have session token
      if (sessionToken.has()) {
        var configUrl = url.parse(config.url);
        // add session token only for api urls
        if (configUrl.host === apiUrl.host) {
          config.headers.SessionId = sessionToken.get();
        }
      }
      return config;
    }
  };
}

export default sessionTokenHttpInterceptor;
