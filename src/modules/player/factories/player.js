import _ from "lodash";
import assert from "assert";
import {EventEmitter} from "events";

const EVENT_PLAYER_STATE_CHANGE = "visibilityStateChange";

function player (songStore) {
  "ngInject";

  var events = new EventEmitter();
  var songEntity, active;

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
     * check if player is active
     * @return {boolean}
     */
    isActive() {
      return !!active;
    },
    /**
     * get currently playing song entity
     * @return {SongInfoEntity|undefined}
     */
    getSongEntity() {
      return songEntity;
    },
    /**
     * start player
     * @param {string} songId
     */
    async play(songId) {
      assert.ok(_.isString(songId), "Song Id must be defined as string!");
      songEntity = await songStore.fetch(songId);
      active = true;
      events.emit(EVENT_PLAYER_STATE_CHANGE);
    },
    /**
     * stop player
     */
    stop() {
      songEntity = undefined;
      active = false;
      events.emit(EVENT_PLAYER_STATE_CHANGE);
    }
  };
}

export default player;
