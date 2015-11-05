import datastoreModule from 'modules/datastore/module';

describe('myLibraryStore', () => {
  let myLibraryStore, request, entityMapper, SongListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_myLibraryStore_, _SongListEntity_) => {
      myLibraryStore = _myLibraryStore_;
      SongListEntity = _SongListEntity_;
    });
  });

  describe('fetch', () => {
    it('should request the list of songs', () => {
      (async () => {
        await myLibraryStore.fetch();
        expect(request.get).toHaveBeenCalledWith('/msl/v1/accountedge/users/mylibrary');
      })();
    });

    it('should map the response into a SongListEntity', () => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await myLibraryStore.fetch();
        expect(entityMapper).toHaveBeenCalledWith(response, SongListEntity);
      })();
    });
  });
});
