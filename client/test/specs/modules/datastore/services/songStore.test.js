import {PAGE_SIZE} from 'constants.js';
import datastoreModule from 'modules/datastore/module';

describe('songStore', () => {
  const API_PATH = `:${process.env.CATALOG_PORT}/msl/v1/catalogedge`;
  const error = new Error('an error');

  let songStore, request, entityMapper, SongInfoEntity, SongListEntity, $log;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');
      $log = jasmine.createSpyObj('$log', ['error']);

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
      $provide.value('$log', $log);
    });

    inject((_songStore_, _SongInfoEntity_, _SongListEntity_) => {
      songStore = _songStore_;
      SongInfoEntity = _SongInfoEntity_;
      SongListEntity = _SongListEntity_;
    });
  });

  describe('fetch', () => {
    const SONG_ID = '3';
    const response = { data: 'a_response' };

    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should get the song info from the api endpoint', (done) => {
      (async () => {
        await songStore.fetch(SONG_ID);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/song/${ SONG_ID }`);
        done();
      })();
    });

    it('should map the response into a SongInfoEntity', (done) => {
      (async () => {
        await songStore.fetch(SONG_ID);
        expect(entityMapper).toHaveBeenCalledWith(response.data, SongInfoEntity);
        done();
      })();
    });

    it('should log an error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await songStore.fetch(SONG_ID);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });

  describe('fetchAll', () => {
    const opts = '4';
    const response = { data: 'a_response' };


    beforeEach(() => {
      request.get.and.returnValue(response);
    });

    it('should request the songs to the endpoint', (done) => {
      (async () => {
        const params = { items: PAGE_SIZE, facets: opts };
        await songStore.fetchAll(opts);
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/browse/song`, { params });
        done();
      })();
    });

    it('should map the response into a SongListEntity', (done) => {
      (async () => {
        await songStore.fetchAll(opts);
        expect(entityMapper).toHaveBeenCalledWith(response.data, SongListEntity);
        done();
      })();
    });

    it('should log an error if an error is thrown', (done) => {
      (async () => {
        request.get.and.throwError(error);
        await songStore.fetchAll(opts);
        expect($log.error).toHaveBeenCalledWith(error);
        done();
      })();
    });

  });
});
