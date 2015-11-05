/* global describe, it, expect, beforeEach, inject */
import datastoreModule from 'modules/datastore/module';

describe('logoutStore', () => {
  let logoutStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['post']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_logoutStore_, _StatusResponseEntity_) => {
      logoutStore = _logoutStore_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('push', () => {
    it('should post to the logout endpoint', () => {
      (async () => {
        await logoutStore.push();
        expect(request.post).toHaveBeenCalledWith('/msl/v1/loginedge/logout');
      })();
    });

    it('should map the response inta a StatusResponseEntity', () => {
      (async () => {
        const response = { status: 'ok' };
        request.post.and.returnValue(response);
        await logoutStore.push();
        expect(entityMapper).toHaveBeenCalledWith(response, StatusResponseEntity);
      })();
    });
  });
});
