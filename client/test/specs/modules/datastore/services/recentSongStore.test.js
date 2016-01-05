/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('recentSongsStore', () => {
  const API_PATH = `:${process.env.ACCOUNT_PORT}/msl/v1/accountedge/users`;
  let recentSongsStore, request, entityMapper, SongListEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_recentSongsStore_, _SongListEntity_) => {
      recentSongsStore = _recentSongsStore_;
      SongListEntity = _SongListEntity_;
    });
  });

  describe('fetch', () => {
    it('should request the user recent songs', () => {
      (async () => {
        await recentSongsStore.fetch();
        expect(request.get).toHaveBeenCalledWith(`${API_PATH}/recentsongs`);
      })();
    });

    it('should map the response into a SongListEntity', () => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await recentSongsStore.fetch();
        expect(entityMapper).toHaveBeenCalledWith(response, SongListEntity);
      })();
    });
  });
});
