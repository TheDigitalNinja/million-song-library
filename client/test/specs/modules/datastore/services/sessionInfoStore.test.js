/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('sessionInfoStore', () => {
  let sessionInfoStore, request, entityMapper, SessionInfoEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['get']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_sessionInfoStore_, _SessionInfoEntity_) => {
      sessionInfoStore = _sessionInfoStore_;
      SessionInfoEntity = _SessionInfoEntity_;
    });
  });

  describe('fetch', () => {
    const SESSION_ID = 'abc';

    it('should fetch the session info', () => {
      (async () => {
        await sessionInfoStore.fetch(SESSION_ID);
        expect(request.get).toHaveBeenCalledWith('/api/v1/loginedge/sessioninfo/');
      })();
    });

    it('should map the response into a SessionInfoEntity', () => {
      (async () => {
        const response = 'a_response';
        request.get.and.returnValue(response);
        await sessionInfoStore.fetch(SESSION_ID);
        expect(entityMapper).toHaveBeenCalledWith(response, SessionInfoEntity);
      })();
    });
  });
});
