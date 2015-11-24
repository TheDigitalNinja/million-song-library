import _ from 'lodash';
import assert from 'assert';

const LOGIN_EMPTY = 'Login is empty!';
const PASSWORD_EMPTY = 'Password is empty!';

/**
 * @name authentication
 * @param {$cookies} $cookies
 * @param {authCookie} authCookie
 * @param {loginStore} loginStore
 * @param {logoutStore} logoutStore
 * @returns {*}
 */
export default function authentication (
  $cookies,
  authCookie,
  loginStore,
  logoutStore
) {
  'ngInject';

  return {
    /**
     * authenticate user and create user session
     * and emit state change event
     * @name authentication#authenticate
     * @param {string} login
     * @param {string} password
     */
    async authenticate(login, password) {
      assert.ok(!_.isEmpty(login), LOGIN_EMPTY);
      assert.ok(!_.isEmpty(password), PASSWORD_EMPTY);
      // make api request
      const response = await loginStore.push(login, password);
      if(response.authenticated) {
        authCookie.set(response.authenticated);
      }
    },
    /**
     * destroy user session
     * and emit state change event
     * @name authentication#destory
     */
    async destroy() {
      authCookie.destroy();
      await logoutStore.push();
    },
    /**
     * returns if user is authorized
     * @name authentication#isAuthenticated
     * @return {boolean}
     */
    isAuthenticated() {
      return authCookie.has();
    },
  };
}
