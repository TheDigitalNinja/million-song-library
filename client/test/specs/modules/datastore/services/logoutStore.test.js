/* global describe, it, expect, beforeEach, inject */
import  dataStoreModule from 'modules/datastore/module';

describe('logoutStore', () => {
  const API_PATH = `:${process.env.LOGIN_PORT}/msl/v1/loginedge`;

  let logoutStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module( dataStoreModule, ($provide) => {
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
    const response = { status: 'ok', message: 'successfully logged out' };

    beforeEach(() => {
      request.post.and.returnValue(response);
    });

    it('should post to the logout endpoint', () => {
      (async () => {
        await logoutStore.push();
        expect(request.post).toHaveBeenCalledWith(`${API_PATH}/logout`, jasmine.any(Object));
      })();
    });

    it('should map the response into a StatusResponseEntity', () => {
      (async () => {
        await logoutStore.push();
        expect(entityMapper).toHaveBeenCalledWith(response.data, StatusResponseEntity);
      })();
    });
  });
});
