import datastoreModule from 'modules/datastore/module';

describe('rateAlbumStore', () => {
  const ALBUM_ID = '2';
  const RATING = '4';
  const API_PATH = `:${process.env.RATINGS_PORT}/msl/v1/ratingsedge/ratealbum`;

  let rateAlbumStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['put']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_rateAlbumStore_, _StatusResponseEntity_) => {
      rateAlbumStore = _rateAlbumStore_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('push', () => {
    const response = { data: 'a_response' };
    beforeEach(() => {
      request.put.and.returnValue(response);
    });

    it('should make a put to the ratealbum endpoint', (done) => {
      (async () => {
        const data = `rating=${ RATING }`;
        const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };
        await rateAlbumStore.push(ALBUM_ID, RATING);
        expect(request.put).toHaveBeenCalledWith(`${API_PATH}/${ALBUM_ID}`, data, headers);
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await rateAlbumStore.push(ALBUM_ID, RATING);
        expect(entityMapper).toHaveBeenCalledWith(response.data, StatusResponseEntity);
        done();
      })();
    });
  });
});
