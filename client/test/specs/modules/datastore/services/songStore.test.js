import datastoreModule from 'modules/datastore/module';

describe('songStore', () => {
  let songStore, request, entityMapper, SongInfoEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_songStore_, _SongInfoEntity_) => {
      songStore = _songStore_;
      SongInfoEntity = _SongInfoEntity_;
    });
  });

  describe('fetch', () => {
    const SONG_ID = '3';

    it('should get the song info from the api endpoint', () => {
      (async () => {
        await songStore.fetch(SONG_ID);
        expect(request.get).toHaveBeenCalledWith(`/api/v1/catalogedge/song/${ SONG_ID }`);
      })();
    });

    it('should map the response into a SongInfoEntity', () => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await songStore.fetch(SONG_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, SongInfoEntity);
      })();
    });
  });
});
