/* global describe, beforeEach, inject, it, expect */
import angular from "angular";
import playerModule from "modules/player/module";

describe("player factory", function () {
  var player;

  beforeEach(angular.mock.module(playerModule));
  beforeEach(inject(function (_player_) {
    player = _player_;
  }));

  it("should throw exception when passing not PlayerEntity entity", function () {
    expect(() => player.play({})).toThrow();
  });

  it("should be inactive when page starts", function () {
    expect(player.isActive()).toBeFalsy();
  });
});
