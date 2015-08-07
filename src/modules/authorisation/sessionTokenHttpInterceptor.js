import url from "url";

function sessionTokenHttpInterceptor (sessionToken) {
  "ngInject";

  var apiUrl = url.parse(process.env.API_HOST);
  return {
    request(config) {
      // check if we have session token
      if (sessionToken.has()) {
        var configUrl = url.parse(config.url);
        // add session token only for api urls
        if (configUrl.host === apiUrl.host) {
          config.headers.sessionToken = sessionToken.get();
        }
      }
      return config;
    }
  };
}

export default sessionTokenHttpInterceptor;
