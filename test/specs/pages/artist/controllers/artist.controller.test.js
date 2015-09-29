/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import artistPage from 'pages/artist/artist.module.js';

describe('artistCtrl', () => {
  let $scope, $state, $stateParams, $controller, artistStore, catalogStore;

  beforeEach(() => {
    angular.mock.module(artistPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      $provide.value('$state', $state);
      $stateParams = { artistId: '' };
      $provide.value('$stateParams', $stateParams);
      artistStore = jasmine.createSpyObj('artistStore', ['fetch']);
      $provide.value('artistStore', artistStore);
      catalogStore = jasmine.createSpyObj('catalogStore', ['fetch']);
      $provide.value('catalogStore', catalogStore);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should redirect to `home` state when $stateParams.artistId is empty string', () => {
    $controller('artistCtrl', {
      $scope: $scope,
      $state: $state,
      $stateParams: $stateParams,
    });
    expect($state.go).toHaveBeenCalledWith('msl.home');
  });

  //it('should get artistInfo', done => async function () {
  //  $stateParams = {artistId: 1};
  //  var artistCtrl = $controller('artistCtrl', {
  //    $scope: $scope,
  //    $stateParams: $stateParams
  //  });
  //  artistStore.fetch.and.returnValue(Promise.resolve());
  //  catalogStore.fetch.and.returnValue(Promise.resolve());
  //  //await artistCtrl.getArtistInfo();
  //  expect(artistStore.fetch).toHaveBeenCalledWith($stateParams.artistId);
  //  expect(catalogStore.fetch).toHaveBeenCalledWith({artist: $stateParams.artistId});
  //  done();
  //}());
});
