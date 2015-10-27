/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('genreStore', () => {
  let genreStore, request, entityMapper, GenreListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_genreStore_, _GenreListEntity_) => {
      genreStore = _genreStore_;
      GenreListEntity = _GenreListEntity_;
    });
  });

  describe('fetch', () => {
    it('should get the facet for the given genre', (done) => {
      (async () => {
        const GENRE = 'rock';

        await genreStore.fetch(GENRE);
        expect(request.get).toHaveBeenCalledWith(`/api/v1/catalogedge/facet/${GENRE}`);
        done();
      })();
    });

    it('should use the default facet when called without genre', (done) => {
      (async () => {
        await genreStore.fetch();
        expect(request.get).toHaveBeenCalledWith('/api/v1/catalogedge/facet/~');
        done();
      })();
    });

    it('should map the response into a GenreList', (done) => {
      (async () => {
        const response = { facets: [] };
        request.get.and.returnValue(response);

        await genreStore.fetch();
        expect(entityMapper).toHaveBeenCalledWith(response, GenreListEntity);
        done();
      })();
    });
  });
});
