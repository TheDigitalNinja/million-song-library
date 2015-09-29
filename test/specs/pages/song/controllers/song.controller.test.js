/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import songPage from 'pages/song/song.module.js';

describe('songCtrl', () => {
  let $scope, $state, $stateParams, $controller, songStore;

  beforeEach(() => {
    angular.mock.module(songPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      $provide.value('$state', $state);
      $stateParams = { songId: '' };
      $provide.value('$stateParams', $stateParams);
      songStore = jasmine.createSpyObj('songStore', ['fetch']);
      $provide.value('songStore', songStore);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should redirect to `home` state when $stateParams.songId is empty string', () => {
    $controller('songCtrl', {
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get artistInfo', (done) => {
    (async () => {
      const songCtrl = $controller('songCtrl', {
        $scope: $scope,
        $stateParams: { songId: '' },
      });

      songStore.fetch.and.returnValue(Promise.resolve());
      songCtrl.songId = '';
      await songCtrl.getSongInfo();
      expect(songStore.fetch).toHaveBeenCalledWith($stateParams.songId);
      done();
    })();
  });
});
