import assert from "assert";
import {EventEmitter} from "events";
import PlayerEntity from "./entities/PlayerEntity";

const EVENT_PLAYER_STATE_CHANGE = "visibilityStateChange";
const NOT_PLAYER_ENTITY = "Provided entity is not instance of PlayerEntity!";

function player () {
  "ngInject";

  var events = new EventEmitter();
  var active = false;

  return {
    /**
     * player state change listener
     *  - player state changes when track is added to play list or is removed from it
     * @todo add more details on its workflow
     * @param {Function} cb
     */
    addStateChangeListener(cb) {
      cb();
      events.on(EVENT_PLAYER_STATE_CHANGE, cb);
    },
    /**
     * remove player stage listener
     * @param {Function} cb
     */
    removeStateChangeListener(cb) {
      events.removeListener(EVENT_PLAYER_STATE_CHANGE, cb);
    },
    /**
     * @return {boolean}
     */
    isActive() {
      return active;
    },
    /**
     * @param {PlayerEntity} entity
     */
    play(entity) {
      assert.ok(entity instanceof PlayerEntity, NOT_PLAYER_ENTITY);
    }
  };
}

export default player;
