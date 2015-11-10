import _ from 'lodash';
import assert from 'assert';

const LOGIN_EMPTY = 'Login is empty!';
const PASSWORD_EMPTY = 'Password is empty!';

/**
 * @name authorisation
 * @param {$cookies} $cookies
 * @param {sessionToken} sessionToken
 * @param {loginStore} loginStore
 * @param {logoutStore} logoutStore
 * @returns {*}
 */
export default function authorisation (
  $cookies,
  sessionToken,
  loginStore,
  logoutStore
) {
  'ngInject';

  return {
    /**
     * authorise user and create user session
     * and emit state change event
     * @name authorisation#authorise
     * @param {string} login
     * @param {string} password
     */
    async authorise(login, password) {
      assert.ok(!_.isEmpty(login), LOGIN_EMPTY);
      assert.ok(!_.isEmpty(password), PASSWORD_EMPTY);
      // make api request
      const response = await loginStore.push(login, password);
      if(response.sessionToken) {
        const token = response.sessionToken;
        // save session token
        sessionToken.set(token);
      }
    },
    /**
     * destroy user session
     * and emit state change event
     * @name authorisation#destory
     */
    async destroy() {
      sessionToken.destroy();
      await logoutStore.push();
    },
    /**
     * returns if user is authorised
     * @name authorisation#isAuthorised
     * @return {boolean}
     */
    isAuthorised() {
      return sessionToken.has();
    },
  };
}
