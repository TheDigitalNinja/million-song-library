import _ from "lodash";
import assert from "assert";
import querystring from "querystring";
import {EventEmitter} from "events";

const EVENT_CHANGE_NAMESPACE = "change";
const COOKIE_NAMESPACE = "authorisation";
const LOGIN_EMPTY = "Login is empty!";
const PASSWORD_EMPTY = "Password is empty!";

function authorisation ($http, sessionToken, storage) {
  "ngInject";

  var stored = storage.get(COOKIE_NAMESPACE);
  var events = new EventEmitter();
  var authorised = Boolean(stored);
  var authorisedData = stored || {};

  /**
   * when user authorises save data to cookies
   * otherwise delete data from cookies
   */
  function onAuthorisationStateChange () {
    if (authorised) {
      storage.put(COOKIE_NAMESPACE, authorisedData);
    } else {
      storage.remove(COOKIE_NAMESPACE);
    }
  }

  /**
   * attach host to path
   * @returns {string}
   */
  function withHost () {
    return [process.env.API_HOST].concat(_.toArray(arguments)).join("");
  }

  // register authorisation state change event
  events.on(EVENT_CHANGE_NAMESPACE, onAuthorisationStateChange);

  return {
    /**
     * authorise user and create user session
     * and emit state change event
     * @param {{login: string, password: string}} credentials
     */
    async authorise({login: login, password: password}) {
      var data, headers, response, token;
      assert.ok(!_.isEmpty(login), LOGIN_EMPTY);
      assert.ok(!_.isEmpty(password), PASSWORD_EMPTY);
      // make api request
      data = querystring.stringify({email: login, password});
      headers = {"Content-Type": "application/x-www-form-urlencoded"};
      response = await $http.post(withHost("/api/loginedge/login"), data, {headers});
      token = response.data.sessionToken;
      // save session token
      sessionToken.set(token);
      // get user data
      response = await $http.get(withHost("/api/loginedge/sessioninfo/", token));
      // save authorised data
      authorised = true;
      authorisedData = _.pick(response.data, ["userEmail", "userId"]);
      events.emit(EVENT_CHANGE_NAMESPACE);
    },
    /**
     * destroy user session
     * and emit state change event
     */
    async destroy() {
      await $http.post(withHost("/api/loginedge/logout"), {});
      sessionToken.destroy();
      authorised = false;
      authorisedData = {};
      events.emit(EVENT_CHANGE_NAMESPACE);
    },
    /**
     * get user data
     * @param {string|null} param
     * @return {*}
     */
    getUserData(param = null) {
      if (_.isNull(param)) {
        return authorisedData;
      } else {
        return _.result(authorisedData, param);
      }
    },
    /**
     * returns if user is authorised
     * @return {boolean}
     */
    isAuthorised() {
      return authorised;
    },
    /**
     * add authorisation sate change listener
     * @note by default when adding state listener it will fire at first time to report current sate
     * @param {Function} cb
     */
    addChangeListener(cb) {
      cb();
      events.on(EVENT_CHANGE_NAMESPACE, cb);
    },
    /**
     * remove authorisation state change listener
     * @param {Function} cb
     */
    removeChangeListener(cb) {
      events.removeListener(EVENT_CHANGE_NAMESPACE, cb);
    }
  };
}

export default authorisation;
