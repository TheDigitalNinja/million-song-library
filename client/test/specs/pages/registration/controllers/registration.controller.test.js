import registrationModule from 'pages/registration/registration.module.js';

describe('Registration Controller', () => {
  let $scope, registrationCtrl, authentication, registrationStore, $state;

  beforeEach(() => {
    angular.mock.module(registrationModule, ($provide) => {
      authentication = jasmine.createSpyObj('authentication', ['authenticate']);
      registrationStore = jasmine.createSpyObj('registrationStore', ['registration']);
      $state = jasmine.createSpyObj('$state', ['go']);

      $provide.value('authentication', authentication);
      $provide.value('registrationStore', registrationStore);
      $provide.value('$state', $state);
    });

    inject(($rootScope, $controller) => {
      $scope = $rootScope.$new();
      registrationCtrl = () => {
        const controller = $controller('registrationCtrl', { $scope });
        $scope.$digest();
        return controller;
      };
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  describe('submit', () => {
    const email = 'a@a.com';
    const password = 'paSSw0rd!!';
    const confirmationPassword = 'paSSw0rd!!';

    it('should call the registration store', (done) => {
      (async () => {
        const controller = registrationCtrl();

        controller.email = email;
        controller.password = password;
        controller.confirmationPassword = confirmationPassword;

        await controller.submit();

        expect(registrationStore.registration).toHaveBeenCalledWith(email, password, confirmationPassword);
        done();
      })();
    });

    it('should authenticate the user', (done) => {
      (async () => {
        const controller = registrationCtrl();
        controller.email = email;
        controller.password = password;

        await controller.submit();

        expect(authentication.authenticate).toHaveBeenCalledWith(email, password);
        done();
      })();
    });

    it('should go to the home page', (done) => {
      (async () => {
        const controller = registrationCtrl();
        await controller.submit();

        expect($state.go).toHaveBeenCalledWith('msl.home');
        done();
      })();
    });

    it('should set hasError when an error is raised', (done) => {
      (async () => {
        const error = new Error();
        const controller = registrationCtrl();

        registrationStore.registration.and.throwError(error);
        await controller.submit();

        expect(controller.hasError).toBeTruthy();
        done();
      })();
    });
  });
});
