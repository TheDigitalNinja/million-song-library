/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';
import ArtistListEntity from 'modules/datastore/entities/ArtistListEntity';

describe('artistStore', () => {
  const ARTIST_ID = '3';
  let artistStore, entityMapper, request, ArtistInfoEntity, ArtistListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_artistStore_, _ArtistInfoEntity_, _ArtistListEntity_) => {
      artistStore = _artistStore_;
      ArtistInfoEntity = _ArtistInfoEntity_;
      ArtistListEntity = _ArtistListEntity_;
    });
  });

  describe('fetch', () => {
    it('should get the artist', (done) => {
      (async () => {
        await artistStore.fetch(ARTIST_ID);
        expect(request.get).toHaveBeenCalledWith(`/api/v1/catalogedge/artist/${ARTIST_ID}`);
        done();
      })();
    });

    it('should map the get album info into an AlbumInfoEntity', (done) => {
      (async () => {
        const response = { name: 'lala' };
        request.get.and.returnValue(response);
        await artistStore.fetch(ARTIST_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, ArtistInfoEntity);
        done();
      })();
    });
  });

  describe('fetchAll', () => {
    it('should fetch all the artists', (done) => {
      (async () => {
        const GENRE = 'rock';
        const params = { params: { facets: GENRE} };
        await artistStore.fetchAll(GENRE);
        expect(request.get).toHaveBeenCalledWith('/api/v1/catalogedge/browse/artist', params);
        done();
      })();
    });

    it('should map the browse album list into an AlbumListEntity', (done) => {
      (async () => {
        const response = { artists: [] };
        request.get.and.returnValue(response);
        await artistStore.fetchAll('rock');
        expect(entityMapper).toHaveBeenCalledWith(response, ArtistListEntity);
        done();
      })();
    });
  });
});
