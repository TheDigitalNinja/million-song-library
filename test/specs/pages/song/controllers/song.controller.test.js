/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import songPage from 'pages/song/song.module.js';

describe('songCtrl', () => {
  let $scope, $state, $stateParams, $controller, songModel, artistModel, $log;

  beforeEach(() => {
    angular.mock.module(songPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      $provide.value('$state', $state);
      $stateParams = { songId: '' };
      $provide.value('$stateParams', $stateParams);
      songModel = jasmine.createSpyObj('songModel', ['getSong']);
      $provide.value('songModel', songModel);
      $provide.value('artistModel', artistModel);
      $provide.value('$log', $log);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should instantiate a song controller', () => {
    const songCtrl = $controller('songCtrl', {
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
      songModel: songModel,
    });
    expect(songCtrl).toBeDefined();
  });

  it('should redirect to `home` state when $stateParams.songId is empty string', () => {
    $controller('songCtrl', {
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
      songModel: songModel,
    });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get songInfo when stateParam songId is defined', (done) => {
    (async () => {
      const SONG_ID = '1';
      $stateParams = { songId: SONG_ID };
      const songCtrl = $controller('songCtrl', {
        artistModel: artistModel,
        $log: $log,
        $scope: $scope,
        $state: $state,
        $stateParams: $stateParams,
        songModel: songModel,
      });
      songModel.getSong.and.returnValue(Promise.resolve());
      await songModel.getSong(SONG_ID);
      expect(songModel.getSong).toHaveBeenCalledWith(SONG_ID);
      done();
    })();
  });
});
