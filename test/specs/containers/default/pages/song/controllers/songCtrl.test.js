/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import angular from "angular";
import defaultSongPage from "containers/default/pages/song/module";

describe("songCtrl", function () {
  var $scope, $state, $stateParams, $controller, songStore;

  beforeEach(angular.mock.module(defaultSongPage, function ($provide) {
    $state = jasmine.createSpyObj("$state", ["go"]);
    $provide.value("$state", $state);
    $stateParams = { songId: "" };
    $provide.value("$stateParams", $stateParams);
    songStore = jasmine.createSpyObj("songStore", ["fetch"]);
    $provide.value("songStore", songStore);
  }));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it("should redirect to 'default.home' state when $stateParams.songId is empty string", function () {
    $controller("songCtrl", {
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams
    });
    expect($state.go).toHaveBeenCalledWith("default.home");
  });

  it("should get artistInfo", done => async function () {
    var songCtrl = $controller("songCtrl", {$scope});
    songStore.fetch.and.returnValue(Promise.resolve());
    await songCtrl.getSongInfo();
    expect(songStore.fetch).toHaveBeenCalledWith($stateParams.songId);
    done();
  }());
});
