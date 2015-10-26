/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import albumModule from 'pages/album/album.module.js';

describe('albumCtrl', () => {
  let albumModel, artistModel, $log, $scope, $state, $stateParams, $controller;

  beforeEach(() => {
    angular.mock.module(albumModule, ($provide) => {

      $state = jasmine.createSpyObj('$state', ['go']);
      $provide.value('$state', $state);

      $stateParams = { albumId: '' };
      $provide.value('$stateParams', $stateParams);

      albumModel = jasmine.createSpyObj('albumModel', ['getAlbum', 'getAlbumSongs']);
      $provide.value('albumModel', albumModel);

      artistModel = jasmine.createSpyObj('artistModel', ['getSimilarArtists']);
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

  it('should create an album controller', () => {
    const albumCtrl = $controller('albumCtrl', {
      albumModel: albumModel,
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    expect(albumCtrl).toBeDefined();
  });

  it('should redirect to `home` state when $stateParams.albumId is empty string', () => {
    $controller('albumCtrl', {
      albumModel: albumModel,
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get album information when stateParam is defined', (done) => {
    (async () => {
      $stateParams = { albumId: 1 };
      $controller('albumCtrl', {
        albumModel: albumModel,
        artistModel: artistModel,
        $log: $log,
        $scope: $scope,
        $state: $state,
        $stateParams: $stateParams,
      });
      albumModel.getAlbum.and.returnValue(Promise.resolve());
      await albumModel.getAlbum($stateParams.albumId);
      expect(albumModel.getAlbum).toHaveBeenCalledWith($stateParams.albumId);
      done();
    })();
  });

  it('should update the album songs when the album changes', () => {
    $stateParams = { albumId: '1' };
    const ctrl = $controller('albumCtrl', {
      albumModel: albumModel,
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    albumModel.getAlbumSongs.and.callFake((artistId, cb) => {
      cb(songs);
    });
    const newAlbum = { artistId: '1' };
    albumModel.album = newAlbum;
    const songs = ['song'];
    $scope.$apply();
    expect(albumModel.getAlbumSongs).toHaveBeenCalledWith(newAlbum.artistId, jasmine.any(Function));
    expect(ctrl.albumSongs).toEqual(songs);
  });

  it('should update the similar artists when album changes', () => {
    $stateParams = { albumId: '1' };
    const ctrl = $controller('albumCtrl', {
      albumModel: albumModel,
      artistModel: artistModel,
      $log: $log,
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    artistModel.getSimilarArtists.and.callFake((artistId, cb) => {
      cb(artists);
    });
    const newAlbum = { artistId: '1' };
    albumModel.album = newAlbum;
    const artists = ['artist'];
    $scope.$apply();
    expect(artistModel.getSimilarArtists).toHaveBeenCalledWith(newAlbum.artistId, jasmine.any(Function));
    expect(ctrl.similarArtists).toEqual(artists);
  });
});
