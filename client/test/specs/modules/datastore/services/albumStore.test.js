/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('albumStore', () => {
  let albumStore, request, entityMapper, AlbumInfoEntity, AlbumListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_albumStore_, _AlbumInfoEntity_, _AlbumListEntity_) => {
      albumStore = _albumStore_;
      AlbumInfoEntity = _AlbumInfoEntity_;
      AlbumListEntity = _AlbumListEntity_;
    });
  });

  describe('fetch', () => {
    const ALBUM_ID = '4';

    it('should request the album', (done) => {
      (async () => {
        await albumStore.fetch(ALBUM_ID);
        expect(request.get).toHaveBeenCalledWith(`/api/v1/catalogedge/album/${ ALBUM_ID }`);
        done();
      })();
    });

    it('should map the album into an AlbumInfoEntity', (done) => {
      (async () => {
        const response = { albumId: ALBUM_ID };
        request.get.and.returnValue(response);
        await albumStore.fetch(ALBUM_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, AlbumInfoEntity);
        done();
      })();
    });
  });

  describe('fetchAll', () => {
    const GENRE = 'rock';

    it('should browse the albums', (done) => {
      (async () => {
        const params = { params: {facets: GENRE } };
        await albumStore.fetchAll(GENRE);
        expect(request.get).toHaveBeenCalledWith('/api/v1/catalogedge/browse/album', params);
        done();
      })();
    });

    it('should map the response into a AlbumListEntity', (done) => {
      (async () => {
        const response = { albums: [] };
        request.get.and.returnValue(response);
        await albumStore.fetchAll(GENRE);
        expect(entityMapper).toHaveBeenCalledWith(response, AlbumListEntity);
        done();
      })();
    });
  });
});
