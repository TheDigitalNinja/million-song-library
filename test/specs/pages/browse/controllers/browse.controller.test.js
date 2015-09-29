/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import browsePage from 'pages/browse/browse.module.js';

describe('browseCtrl', () => {
  const genreName = 'rock';
  const rating = 2;
  const artistMbid = 1;

  let $scope, $stateParams, $controller, catalogStore, browseCtrl;

  beforeEach(() => {
    angular.mock.module(browsePage, ($provide) => {
      $stateParams = { genre: genreName, rating: rating, artist: artistMbid };
      $provide.value('$stateParams', $stateParams);
      catalogStore = jasmine.createSpyObj('catalogStore', ['fetch']);
      $provide.value('catalogStore', catalogStore);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });

    catalogStore.fetch.and.returnValue(Promise.resolve());

    browseCtrl = $controller('browseCtrl', {
      $scope: $scope,
      stateParams: $stateParams,
    });
  });

  afterEach(() => { $scope.$destroy(); });

  it('should get the songs filtering by genre, artist, and rating', (done) => {
    (async () => {
      expect(catalogStore.fetch).toHaveBeenCalledWith({
        genre: genreName,
        rating: rating,
        artist: artistMbid,
      });

      done();
    })();
  });
});
