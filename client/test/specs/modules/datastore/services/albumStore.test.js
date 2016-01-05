/* global describe, it, expect, beforeEach, inject */
import {PAGE_SIZE} from 'constants.js';
import datastoreModule from 'modules/datastore/module';

describe('albumStore', () => {
  const API_PATH = `:${process.env.CATALOG_PORT}/msl/v1/catalogedge`;
  const error = new Error('an error');

  let albumStore, request, entityMapper, AlbumInfoEntity, AlbumListEntity, $log;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');
      $log = jasmine.createSpyObj('$log', ['error']);

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
      $provide.value('$log', $log);
    });

    inject((_albumStore_, _AlbumInfoEntity_, _AlbumListEntity_) => {
      albumStore = _albumStore_;
      AlbumInfoEntity = _AlbumInfoEntity_;
      AlbumListEntity = _AlbumListEntity_;
    });
  });

  describe('fetch', () => {
    const ALBUM_ID = '4';
    const response = { data: { albumId: ALBUM_ID }};

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should request the album', (done) => {
      (async () => {
        await albumStore.fetch(ALBUM_ID);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/album/${ ALBUM_ID }`);
        done();
      })();
    });

    it('should map the album into an AlbumInfoEntity', (done) => {
      (async () => {
        await albumStore.fetch(ALBUM_ID);
        expect(entityMapper).toHaveBeenCalledWith(response.data, AlbumInfoEntity);
        done();
      })();
    });

    it('should log an error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await albumStore.fetch(ALBUM_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

  });

  describe('fetchAll', () => {
    const GENRE = 'rock';
    const response = { data: { albums: [] } };

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should browse the albums', (done) => {
      (async () => {
        const params = { params: { items: PAGE_SIZE, facets: GENRE } };
        await albumStore.fetchAll(GENRE);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/browse/album`, params);
        done();
      })();
    });

    it('should map the response into a AlbumListEntity', (done) => {
      (async () => {
        await albumStore.fetchAll(GENRE);
        expect(entityMapper).toHaveBeenCalledWith(response.data, AlbumListEntity);
        done();
      })();
    });

    it('should log an error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await albumStore.fetchAll(GENRE);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

  });
});
