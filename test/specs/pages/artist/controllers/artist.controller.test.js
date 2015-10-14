/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import artistModule from 'pages/artist/artist.module.js';

describe('artistCtrl', () => {
  let $scope, $state, $stateParams, $controller, artistModel;

  beforeEach(() => {
    angular.mock.module(artistModule, ($provide) => {

      $state = jasmine.createSpyObj('$state', ['go']);
      $provide.value('$state', $state);

      $stateParams = { artistId: '' };
      $provide.value('$stateParams', $stateParams);

      artistModel = jasmine.createSpyObj('artistModel', ['getArtist']);
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

  it('should create an artist controller', () => {
    const artistCtrl = $controller('artistCtrl', {
      artistModel: artistModel,
      $scope: $scope,
      $stateParams: $stateParams,
      $state: $state,
    });
    expect(artistCtrl).toBeDefined();
  });

  it('should redirect to `home` state when $stateParams.artistId is empty string', () => {
    $controller('artistCtrl', {
      artistModel: artistModel,
      $scope: $scope,
      $stateParams: $stateParams,
      $state: $state,
    });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  it('should get artistInfo when stateParam artistId is defined', (done) => {
    (async () => {
      $stateParams = { artistId: 1 };
      $controller('artistCtrl', {
        artistModel: artistModel,
        $scope: $scope,
        $stateParams: $stateParams,
        $state: $state,
      });
      artistModel.getArtist.and.returnValue(Promise.resolve());
      await artistModel.getArtist($stateParams.artistId);
      expect(artistModel.getArtist).toHaveBeenCalledWith($stateParams.artistId);
      done();
    })();
  });
});
