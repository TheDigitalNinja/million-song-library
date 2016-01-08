/* global describe, it, expect, beforeEach, inject */
import {PAGE_SIZE} from 'constants.js';
import datastoreModule from 'modules/datastore/module';

describe('artistStore', () => {
  const API_PATH = `:${process.env.CATALOG_PORT}/msl/v1/catalogedge`;
  const error = new Error('an error');

  let artistStore, entityMapper, request, ArtistInfoEntity, ArtistListEntity, $log;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');
      $log = jasmine.createSpyObj('$log', ['error']);

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
      $provide.value('$log', $log);
    });

    inject((_artistStore_, _ArtistInfoEntity_, _ArtistListEntity_) => {
      artistStore = _artistStore_;
      ArtistInfoEntity = _ArtistInfoEntity_;
      ArtistListEntity = _ArtistListEntity_;
    });
  });

  describe('fetch', () => {
    const ARTIST_ID = '3';
    const response = { data: { name: 'someArtist' } };

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should get the artist', (done) => {
      (async () => {
        await artistStore.fetch(ARTIST_ID);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/artist/${ARTIST_ID}`);
        done();
      })();
    });

    it('should map the get album info into an AlbumInfoEntity', (done) => {
      (async () => {
        await artistStore.fetch(ARTIST_ID);
        expect(entityMapper).toHaveBeenCalledWith(response.data, ArtistInfoEntity);
        done();
      })();
    });

    it('should log an error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await artistStore.fetch(ARTIST_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('fetchAll', () => {
    const response = { data: { artists: [] } };
    const GENRE = 'rock';

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should fetch all the artists', (done) => {
      (async () => {

        const params = { params: { items: PAGE_SIZE, facets: GENRE } };
        await artistStore.fetchAll(GENRE);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/browse/artist`, params);
        done();
      })();
    });

    it('should map the browse album list into an AlbumListEntity', (done) => {
      (async () => {
        await artistStore.fetchAll(GENRE);
        expect(entityMapper).toHaveBeenCalledWith(response.data, ArtistListEntity);
        done();
      })();
    });

    it('should log a error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await artistStore.fetchAll(GENRE);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

  });
});
