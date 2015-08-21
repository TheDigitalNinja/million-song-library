/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import angular from "angular";
import defaultArtistPage from "containers/default/pages/artist/module";

describe("artistCtrl", function () {
  var $scope, $state, $stateParams, $controller, artistStore, catalogStore;

  beforeEach(angular.mock.module(defaultArtistPage, function ($provide) {
    $state = jasmine.createSpyObj("$state", ["go"]);
    $provide.value("$state", $state);
    $stateParams = { artistId: "" };
    $provide.value("$stateParams", $stateParams);
    artistStore = jasmine.createSpyObj("artistStore", ["fetch"]);
    $provide.value("artistStore", artistStore);
    catalogStore = jasmine.createSpyObj("catalogStore", ["fetch"]);
    $provide.value("catalogStore", catalogStore);
  }));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it("should redirect to 'default.home' state when $stateParams.artistId is empty string", function () {
    $controller("artistCtrl", {
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams
    });
    expect($state.go).toHaveBeenCalledWith("default.home");
  });

  it("should get artistInfo", done => async function () {
    var artistCtrl = $controller("artistCtrl", {$scope});
    artistStore.fetch.and.returnValue(Promise.resolve());
    catalogStore.fetch.and.returnValue(Promise.resolve());
    await artistCtrl.getArtistInfo();
    expect(artistStore.fetch).toHaveBeenCalledWith($stateParams.artistId);
    expect(catalogStore.fetch).toHaveBeenCalledWith({artist: $stateParams.artistId});
    done();
  }());
});
