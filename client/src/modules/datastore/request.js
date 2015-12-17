/**
 * @param {$http} $http
 * @returns {*}
 */
export default function request ($http) {
  'ngInject';

  /**
   * append api host to request path
   * @param {string} path
   * @returns {string}
   */
  function withHost (path) {
    return [process.env.API_HOST, path].join('');
  }

  return {
    /**
     * @name request#get
     * @param {string} path
     * @param {Object} config
     * @return {*}
     */
    async get(path, config = {}) {
      const response = await $http.get(withHost(path), config);
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
      const response = await $http.post(withHost(path), content, config);
      return response.data;
    },
    /**
     * @name request#put
     * @param {string} path
     * @param {*} content
     * @param {Object} config
     * @return {*}
     */
    async put(path, content = null, config = {}) {
      const response = await $http.put(withHost(path), content, config);
      return response.data;
    },
    /**
     * @name request#delete
     * @param {string} path
     * @param {*} content
     * @param {Object} config
     * @return {*}
     */
    async delete(path, content = null, config = {}) {
      const response = await $http.delete(withHost(path), content, config);
      return response.data;
    },
  };
}
