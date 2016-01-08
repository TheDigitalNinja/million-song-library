import datastoreModule from 'modules/datastore/module';

describe('registrationStore', () => {
  const EMAIL = 'a@a.com';
  const PASSWORD = 'password';
  const PASSWORDCONFIRMATION = 'password';
  const API_PATH = `:${process.env.ACCOUNT_PORT}/msl/v1/accountedge/users`;

  let registrationStore, request, entityMapper, StatusResponseEntity;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      request = jasmine.createSpyObj('request', ['post']);
      entityMapper = jasmine.createSpy('entityMapper');

      $provide.value('request', request);
      $provide.value('entityMapper', entityMapper);
    });

    inject((_registrationStore_, _StatusResponseEntity_) => {
      registrationStore = _registrationStore_;
      StatusResponseEntity = _StatusResponseEntity_;
    });
  });

  describe('post', () => {

    const response = { data: 'a response' };
    beforeEach(() => {
      request.post.and.returnValue(response);
    });

    it('should do a post request to the registration edge', (done) => {
      (async () => {
        await registrationStore.registration(EMAIL, PASSWORD, PASSWORD);
        var params = { email: EMAIL, password: PASSWORD, passwordConfirmation: PASSWORDCONFIRMATION };
        expect(request.post).toHaveBeenCalledWith(`${API_PATH}/register`, params, jasmine.any(Object));
        done();
      })();
    });

    it('should map the response into a StatusResponseEntity', (done) => {
      (async () => {
        await registrationStore.registration(EMAIL, PASSWORD, PASSWORDCONFIRMATION);
        expect(entityMapper).toHaveBeenCalledWith(response.data, StatusResponseEntity);
        done();
      })();
    });
  });
});
