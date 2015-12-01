/**
 * session token storage service
 * @name authCookie
 * @param {$cookies} $cookies
 * @returns {*}
 */
export default function authCookie ($cookies) {
  'ngInject';

  const STORAGE_NAMESPACE = 'authenticated';

  return {
    /**
     * save session token
     * @name authCookie#set
     * @param {string} token
     * @return {authCookie}
     */
    set(token) {
      $cookies.put(STORAGE_NAMESPACE, token);
      return this;
    },
    /**
     * get current session token
     * @name authCookie#get
     * @return {*}
     */
    get() {
      return $cookies.get(STORAGE_NAMESPACE);
    },
    /**
     * check if session token is set
     * @name authCookie#has
     * @return {boolean}
     */
    has() {
      return !!$cookies.get(STORAGE_NAMESPACE);
    },
    /**
     * destroy session token
     * @name authCookie#destroy
     * @return {authCookie}
     */
    destroy() {
      $cookies.remove(STORAGE_NAMESPACE);
      return this;
    },
  };
}
