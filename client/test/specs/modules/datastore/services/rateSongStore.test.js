import datastoreModule from 'modules/datastore/module';

describe('rateSongStore', () => {
  const SONG_ID = '2';
  const RATING = '4';

  let rateSongStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['put']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_rateSongStore_, _StatusResponseEntity_) => {
      rateSongStore = _rateSongStore_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('push', () => {
    const response = { data: 'a_response' };
    beforeEach(() => {
      request.put.and.returnValue(response);
    });

    it('should make a put to the ratesong endpoint', () => {
      (async () => {
        await rateSongStore.push(SONG_ID, RATING);
        expect(request.put).toHaveBeenCalledWith(`/msl/v1/ratingsedge/ratesong/${SONG_ID}`, `rating=${ RATING }}`, jasmine.any(Object));
      })();
    });

    it('should map the response into a StatusResponseEntity', () => {
      (async () => {
        await rateSongStore.push(SONG_ID, RATING);
        expect(entityMapper).toHaveBeenCalledWith(response.data, StatusResponseEntity);
      })();
    });
  });
});
