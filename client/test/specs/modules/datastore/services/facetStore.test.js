/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('facetStore', () => {
  const API_PATH = `:${process.env.CATALOG_PORT}/msl/v1/catalogedge/facet`;

  let facetStore, request, entityMapper, FacetListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_facetStore_, _FacetListEntity_) => {
      facetStore = _facetStore_;
      FacetListEntity = _FacetListEntity_;
    });
  });

  describe('fetch', () => {
    const response = { data: { name: 'someFacet' } };

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should get the facet for the given facet', (done) => {
      (async () => {
        const FACET = '4';

        await facetStore.fetch(FACET);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/${FACET}`);
        done();
      })();
    });

    it('should use the default facet when called without facet', (done) => {
      (async () => {
        await facetStore.fetch();
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/~`);
        done();
      })();
    });

    it('should map the response into a FacetList', (done) => {
      (async () => {
        const response = { facets: [] };
        request.get.and.returnValue(response);

        await facetStore.fetch();
        expect(entityMapper).toHaveBeenCalledWith(response.data, FacetListEntity);
        done();
      })();
    });
  });
});
