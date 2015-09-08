/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryPage from 'pages/user/user.module.js';

describe('userHistoryCtrl', function () {
  var $scope, $controller, recentSongsStore;

  beforeEach(
    angular.mock.module(libraryPage, function ($provide) {
        recentSongsStore = jasmine.createSpyObj('recentSongsStore', ['fetch']);
        $provide.value('recentSongsStore', recentSongsStore);
      }
    ));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it('should fetch recent history store content on initialization', done => async function () {
    var userHistoryCtrl = $controller('userHistoryCtrl', {
      $scope: $scope,
      recentSongsStore: recentSongsStore
    });
    recentSongsStore.fetch.and.returnValue(Promise.resolve());
    expect(recentSongsStore.fetch).toHaveBeenCalled();
    done();
  }());

});
