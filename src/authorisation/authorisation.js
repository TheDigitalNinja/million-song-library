import {EventEmitter} from "events";

const EVENT_CHANGE_NAMESPACE = "change";

function authorisation () {
  "ngInject";

  var events = new EventEmitter();
  var authorised = false;

  return {
    /**
     * triggers authorisation
     * @param {boolean} state
     */
    authorise(state) {
      authorised = state;
      events.emit(EVENT_CHANGE_NAMESPACE);
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
