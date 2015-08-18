/* global describe, beforeEach, afterEach, inject, it, jasmine, expect */
import _ from "lodash";
import $ from "jquery";
import angular from "angular";
import playerModule from "modules/player/module";

describe("play button directive", function () {
  /** @type {player} */
  var player;
  var $scope;
  var $compile;

  beforeEach(angular.mock.module(playerModule, function ($provide) {
    player = jasmine.createSpyObj("player", ["stop", "play", "getSongEntity", "addStateChangeListener", "removeStateChangeListener"]);
    $provide.value("player", player);
  }));

  beforeEach(inject(function (_$compile_, $rootScope) {
    $scope = $rootScope.$new();
    $compile = _$compile_;
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it("should create play button without size class", function () {
    var template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find("button").hasClass("btn-xs")).toBeFalsy();
    expect(_.trim(template.text())).toBe("Play");
  });

  it("should create xs size play button", function () {
    var template = $($compile(`<play-button size="xs" song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find("button").hasClass("btn-xs")).toBeTruthy();
    expect(_.trim(template.text())).toBe("Play");
  });

  it("should change to stop button when playing current track", function () {
    var onPlayerStateChange;
    player.getSongEntity.and.returnValue({songId: "id"});
    player.addStateChangeListener.and.callFake(fn => onPlayerStateChange = fn);
    var template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find(".fa.fa-stop").length).toBe(0);
    expect(_.trim(template.text())).toBe("Play");
    onPlayerStateChange();
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(0);
    expect(template.find(".fa.fa-stop").length).toBe(1);
    expect(_.trim(template.text())).toBe("Stop");
    expect(player.getSongEntity).toHaveBeenCalled();
  });

  it("should not change to stop button when playing other track", function () {
    var onPlayerStateChange;
    player.getSongEntity.and.returnValue({songId: "id2"});
    player.addStateChangeListener.and.callFake(fn => onPlayerStateChange = fn);
    var template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find(".fa.fa-stop").length).toBe(0);
    expect(_.trim(template.text())).toBe("Play");
    onPlayerStateChange();
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find(".fa.fa-stop").length).toBe(0);
    expect(_.trim(template.text())).toBe("Play");
    expect(player.getSongEntity).toHaveBeenCalled();
  });

  it("should change to stop button when clicking on the button", function () {
    player.getSongEntity.and.returnValue({songId: "id"});
    var template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    template.find("button").click();
    $scope.$digest();
    expect(player.play).toHaveBeenCalledWith("id");
  });

  it("should stop playing track", function () {
    var onPlayerStateChange;
    player.getSongEntity.and.returnValue({songId: "id"});
    player.addStateChangeListener.and.callFake(fn => onPlayerStateChange = fn);
    var template = $($compile(`<play-button song-id="'id'"></play-button>`)($scope));
    $scope.$digest();
    expect(template.find(".fa.fa-play").length).toBe(1);
    expect(template.find(".fa.fa-stop").length).toBe(0);
    expect(_.trim(template.text())).toBe("Play");
    onPlayerStateChange();
    $scope.$digest();
    template.find("button").click();
    $scope.$digest();
    expect(player.stop).toHaveBeenCalled();
  });
});
