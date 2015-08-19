/* global describe, beforeEach, inject, it, jasmine, expect */
import angular from "angular";
import playerModule from "modules/player/module";

describe("player factory", function () {
  /** @type {player} */
  var player;
  var songStore;

  beforeEach(angular.mock.module(playerModule, function ($provide) {
    songStore = jasmine.createSpyObj("songStore", ["fetch"]);
    $provide.value("songStore", songStore);
  }));

  beforeEach(inject(function (_player_) {
    player = _player_;
  }));

  it("should not be active when player has no state set", function () {
    expect(player.isActive()).toBeFalsy();
    expect(player.getSongEntity()).toBeUndefined();
  });

  it("should set song state", done => async function () {
    songStore.fetch.and.returnValue(Promise.resolve({id: "id"}));
    await player.play("id");
    expect(songStore.fetch).toHaveBeenCalledWith("id");
    expect(player.isActive()).toBeTruthy();
    expect(player.getSongEntity()).toEqual({id: "id"});
    done();
  }());

  it("should set song state and then remove it", done => async function () {
    songStore.fetch.and.returnValue(Promise.resolve({id: "id"}));
    await player.play("id");
    expect(player.isActive()).toBeTruthy();
    expect(player.getSongEntity()).toEqual({id: "id"});
    player.stop();
    expect(player.isActive()).toBeFalsy();
    expect(player.getSongEntity()).toBeUndefined();
    done();
  }());

  it("should call state change event when playing song", done => async function () {
    var bind = jasmine.createSpy("addStateChangeListener");
    songStore.fetch.and.returnValue(Promise.resolve({id: "id"}));
    player.addStateChangeListener(bind);
    expect(bind.calls.count()).toBe(1);
    await player.play("id");
    expect(bind.calls.count()).toBe(2);
    done();
  }());

  it("should call state change event when stopping song", done => async function () {
    var bind = jasmine.createSpy("addStateChangeListener");
    songStore.fetch.and.returnValue(Promise.resolve({id: "id"}));
    player.addStateChangeListener(bind);
    expect(bind.calls.count()).toBe(1);
    await player.play("id");
    expect(bind.calls.count()).toBe(2);
    player.stop();
    expect(bind.calls.count()).toBe(3);
    done();
  }());

  it("should call state change event and then remove it", done => async function () {
    var bind = jasmine.createSpy("addStateChangeListener");
    songStore.fetch.and.returnValue(Promise.resolve({id: "id"}));
    player.addStateChangeListener(bind);
    expect(bind.calls.count()).toBe(1);
    await player.play("id");
    expect(bind.calls.count()).toBe(2);
    player.removeStateChangeListener(bind);
    player.stop();
    await player.play("id");
    expect(bind.calls.count()).toBe(2);
    done();
  }());
});
