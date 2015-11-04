/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import songPage from 'pages/song/song.module.js';

describe('songCtrl', () => {
  const SONG_ID = '1';
  let $scope, $state, $stateParams, $controller, songModel, artistModel;

  beforeEach(() => {
    angular.mock.module(songPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      $stateParams = { songId: '' };
      songModel = jasmine.createSpyObj('songModel', ['getSong']);
      artistModel = jasmine.createSpyObj('artistModel', ['getSimilarArtists']);

      $provide.value('$state', $state);
      $provide.value('$stateParams', $stateParams);
      $provide.value('songModel', songModel);
      $provide.value('artistModel', artistModel);
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
    const songCtrl = $controller('songCtrl', { $scope: $scope });
    expect(songCtrl).toBeDefined();
  });

  it('should redirect to `home` state when $stateParams.songId is empty string', () => {
    $controller('songCtrl', { $scope: $scope });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get songInfo when stateParam songId is defined', (done) => {
    (async () => {
      $stateParams = { songId: SONG_ID };
      const songCtrl = $controller('songCtrl', {
        $scope: $scope,
        $stateParams: $stateParams,
      });
      expect(songModel.getSong).toHaveBeenCalledWith(SONG_ID, jasmine.any(Function));
      done();
    })();
  });

  describe('_getSong', () => {
    const song = 'a_song';
    let songCtrl;

    beforeEach(() => {
      $stateParams = { songId: SONG_ID };
      songCtrl = $controller('songCtrl', {
        $scope: $scope,
        $stateParams: $stateParams,
      });
      songModel.getSong.and.callFake((songId, cb) => cb(song));
    });

    it('shoud set the song entity', () => {
      songCtrl._getSong();
      expect(songCtrl.song).toBe(song);
    });

    it('should get the similar artists for the song', () => {
      spyOn(songCtrl, '_getSimilarArtists');
      songCtrl._getSong();
      expect(songCtrl._getSimilarArtists).toHaveBeenCalledWith(song);
    });
  });

  describe('_getSimilarArtists', () => {
    const song = { artistId: 1 };
    const artists = ['an_artist'];
    let songCtrl;

    beforeEach(() => {
      $stateParams = { songId: SONG_ID };
      songCtrl = $controller('songCtrl', {
        $scope: $scope,
        $stateParams: $stateParams,
      });
      artistModel.getSimilarArtists.and.callFake((artistId, cb) => cb(artists));
    });

    it('should get similar artists of the song artist', () => {
      songCtrl._getSimilarArtists(song);
      expect(artistModel.getSimilarArtists).toHaveBeenCalledWith(song.artistId, jasmine.any(Function));
    });

    it('should set the similar artists', () => {
      songCtrl._getSimilarArtists(song);
      expect(songCtrl.similarArtists).toBe(artists);
    });
  });
});
