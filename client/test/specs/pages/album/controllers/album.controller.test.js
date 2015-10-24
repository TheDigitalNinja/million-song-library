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

      albumModel = jasmine.createSpyObj('albumModel', ['getAlbum']);
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
});
