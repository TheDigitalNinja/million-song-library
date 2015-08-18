/**
 * @param {$http} $http
 * @returns {*}
 */
function request ($http) {
  "ngInject";

  /**
   * append api host to request path
   * @param {string} path
   * @returns {string}
   */
  function withHost (path) {
    return [process.env.API_HOST, path].join("");
  }

  return {
    /**
     * @name request#get
     * @param {string} path
     * @param {Object} config
     * @return {*}
     */
    async get(path, config = {}) {
      var response = await $http.get(withHost(path), config);
      return response.data;
    },
    /**
     * @name request#post
     * @param {string} path
     * @param {*} content
     * @param {Object} config
     * @return {*}
     */
    async post(path, content, config = {}) {
      var response = await $http.post(withHost(path), content, config);
      return response.data;
    }
  };
}

export default request;
