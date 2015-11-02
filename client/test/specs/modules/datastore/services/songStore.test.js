import datastoreModule from 'modules/datastore/module';

describe('songStore', () => {
  let songStore, request, entityMapper, SongInfoEntity, SongListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_songStore_, _SongInfoEntity_, _SongListEntity_) => {
      songStore = _songStore_;
      SongInfoEntity = _SongInfoEntity_;
      SongListEntity = _SongListEntity_;
    });
  });

  describe('fetch', () => {
    const SONG_ID = '3';

    it('should get the song info from the api endpoint', (done) => {
      (async () => {
        await songStore.fetch(SONG_ID);
        expect(request.get).toHaveBeenCalledWith(`/api/v1/catalogedge/song/${ SONG_ID }`);
        done();
      })();
    });

    it('should map the response into a SongInfoEntity', (done) => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await songStore.fetch(SONG_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, SongInfoEntity);
        done();
      })();
    });
  });

  describe('fetchAll', () => {
    const opts = { genre: 'rock', rating: 4 };

    it('should request the songs to the endpoint', (done) => {
      (async () => {
        const params = { facets: JSON.stringify(opts) };
        await songStore.fetchAll(opts);
        expect(request.get).toHaveBeenCalledWith('/api/v1/catalogedge/browse/song', { params });
        done();
      })();
    });

    it('should map the response into a SongListEntity', (done) => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await songStore.fetchAll(opts);
        expect(entityMapper).toHaveBeenCalledWith(response, SongListEntity);
        done();
      })();
    });
  });
});
