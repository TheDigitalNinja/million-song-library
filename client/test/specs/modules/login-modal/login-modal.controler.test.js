import loginModalModule from 'modules/login-modal/login-modal.module.js';

describe('loginModalCtrl', () => {
  const EMAIL = 'john@doe.com';
  const PASSWORD = 'pass';

  let $scope, $mdDialog, authentication, $state, loginModal, loginModalCtrl;

  beforeEach(() => {
    angular.mock.module(loginModalModule, ($provide) => {
      $mdDialog = jasmine.createSpyObj('$mdDialog', ['show', 'hide', 'cancel']);
      authentication = jasmine.createSpyObj('authentication', ['authenticate']);
      $state = jasmine.createSpyObj('$state', ['reload']);

      $provide.value('$mdDialog', $mdDialog);
      $provide.value('authentication', authentication);
      $provide.value('$state', $state);
    });

    inject(($rootScope, $controller, _loginModalCtrl_) => {
      $scope = $rootScope.$new();
      loginModalCtrl = new _loginModalCtrl_($scope, $mdDialog, authentication, $state);
      loginModalCtrl.email = EMAIL;
      loginModalCtrl.password = PASSWORD;
    });
  });

  describe('submit', () => {
    it('should call the authentication service', (done) => {
      (async () => {
        await loginModalCtrl.submit();
        expect(authentication.authenticate).toHaveBeenCalledWith(EMAIL, PASSWORD);
        done();
      })();
    });

    it('should reload the state', (done) => {
      (async () => {
        await loginModalCtrl.submit();
        expect($state.reload).toHaveBeenCalled();
        done();
      })();
    });

    it('should hide the modal', (done) => {
      (async () => {
        await loginModalCtrl.submit();
        expect($mdDialog.hide).toHaveBeenCalled();
        done();
      })();
    });

    it('should set hasError to true if the authentication fails', (done) => {
      (async () => {
        const error = new Error();
        authentication.authenticate.and.throwError(error);

        await loginModalCtrl.submit();
        expect(loginModalCtrl.hasError).toBeTruthy();
        done();
      })();
    });
  });

  describe('cancel', () => {
    it('should cancel the dialog', () => {
      loginModalCtrl.cancel();
      expect($mdDialog.cancel).toHaveBeenCalled();
    });
  });
});
