/**
 * session token storage service
 * @name sessionToken
 * @param {$cookies} $cookies
 * @returns {*}
 */
export default function sessionToken ($cookies) {
  'ngInject';

  const STORAGE_NAMESPACE = 'sessionId';

  return {
    /**
     * save session token
     * @name sessionToken#set
     * @param {string} token
     * @return {sessionToken}
     */
    set(token) {
      $cookies.put(STORAGE_NAMESPACE, token);
      return this;
    },
    /**
     * get current session token
     * @name sessionToken#get
     * @return {*}
     */
    get() {
      return $cookies.get(STORAGE_NAMESPACE);
    },
    /**
     * check if session token is set
     * @name sessionToken#has
     * @return {boolean}
     */
    has() {
      return !!$cookies.get(STORAGE_NAMESPACE);
    },
    /**
     * destroy session token
     * @name sessionToken#destroy
     * @return {sessionToken}
     */
    destroy() {
      $cookies.remove(STORAGE_NAMESPACE);
      return this;
    },
  };
}
