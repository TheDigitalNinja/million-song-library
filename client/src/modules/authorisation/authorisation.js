import _ from 'lodash';
import assert from 'assert';
import {EventEmitter} from 'events';

const EVENT_CHANGE_NAMESPACE = 'change';
const COOKIE_NAMESPACE = 'authorisation';
const LOGIN_EMPTY = 'Login is empty!';
const PASSWORD_EMPTY = 'Password is empty!';

/**
 * @name authorisation
 * @param {$cookies} $cookies
 * @param {sessionToken} sessionToken
 * @param {loginStore} loginStore
 * @param {sessionInfoStore} sessionInfoStore
 * @param {logoutStore} logoutStore
 * @returns {*}
 */
export default function authorisation (
  $cookies,
  sessionToken,
  loginStore,
  sessionInfoStore,
  logoutStore
) {
  'ngInject';

  // get cookie info for session storage
  // if we have data stored then user is authorised
  const stored = $cookies.getObject(COOKIE_NAMESPACE);
  const events = new EventEmitter();
  let authorised = Boolean(stored);
  let authorisedData = stored || {};

  /**
   * when user authorises save data to cookies
   * otherwise delete data from cookies
   */
  function onAuthorisationStateChange () {
    if(authorised) {
      $cookies.putObject(COOKIE_NAMESPACE, authorisedData);
    }
    else {
      $cookies.remove(COOKIE_NAMESPACE);
    }
  }

  // register authorisation state change event
  events.on(EVENT_CHANGE_NAMESPACE, onAuthorisationStateChange);

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
      const token = response.sessionToken;
      // save session token
      sessionToken.set(token);
      // save authorised data
      authorisedData = await sessionInfoStore.fetch(token);
      authorised = true;
      events.emit(EVENT_CHANGE_NAMESPACE);
    },
    /**
     * destroy user session
     * and emit state change event
     * @name authorisation#destory
     */
    async destroy() {
      await logoutStore.push();
      sessionToken.destroy();
      authorised = false;
      authorisedData = {};
      events.emit(EVENT_CHANGE_NAMESPACE);
    },
    /**
     * get user data
     * @name authorisation#getUserData
     * @param {string|null} [param]
     * @return {*}
     */
    getUserData(param = null) {
      if(_.isNull(param)) {
        return authorisedData;
      }
      else {
        return _.result(authorisedData, param);
      }
    },
    /**
     * returns if user is authorised
     * @name authorisation#isAuthorised
     * @return {boolean}
     */
    isAuthorised() {
      return authorised;
    },
    /**
     * add authorisation state change listener
     * NOTE: by default when adding state listener it will fire at first time to report current sate
     * @name authorisation#addChangeListener
     * @param {Function} cb
     */
    addChangeListener(cb) {
      cb();
      events.on(EVENT_CHANGE_NAMESPACE, cb);
    },
    /**
     * remove authorisation state change listener
     * @name authorisation#removeChangeListener
     * @param {Function} cb
     */
    removeChangeListener(cb) {
      events.removeListener(EVENT_CHANGE_NAMESPACE, cb);
    },
  };
}
