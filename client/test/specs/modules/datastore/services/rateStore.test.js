import datastoreModule from 'modules/datastore/module';

describe('rateStore', () => {
  const SONG_ID = '2';
  const RATING = '4';

  let rateStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['put']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_rateStore_, _StatusResponseEntity_) => {
      rateStore = _rateStore_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('push', () => {
    it('should make a put to the ratesong endpoint', () => {
      (async () => {
        await rateStore.push(SONG_ID, RATING);
        expect(request.put).toHaveBeenCalledWith(`/api/v1/ratingsedge/ratesong/${SONG_ID}`, { rating: RATING });
      })();
    });

    it('should map the response into a StatusResponseEntity', () => {
      (async () => {
        const response = 'a_response';
        request.put.and.returnValue(response);
        await rateStore.push(SONG_ID, RATING);
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
      })();
    });
  });
});
