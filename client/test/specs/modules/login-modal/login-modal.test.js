import loginModalModule from 'modules/login-modal/login-modal.module.js';
import loginModalTemplate from 'modules/login-modal/login-modal.html';

describe('loginModal', () => {
  let $scope, $mdDialog, loginModal, loginModalCtrl;

  beforeEach(() => {
    angular.mock.module(loginModalModule, ($provide) => {
      $mdDialog = jasmine.createSpyObj('$mdDialog', ['show']);
      loginModalCtrl = jasmine.createSpy('loginModalCtrl');

      $provide.value('$mdDialog', $mdDialog);
      $provide.value('loginModalCtrl', loginModalCtrl);
    });

    inject((_loginModal_) => {
      loginModal = _loginModal_;
    });
  });

  describe('show', () => {
    it('should show a new dialog with the expected options', () => {
      const dialogOptions = {
        controller: loginModalCtrl,
        controllerAs: 'vm',
        template: loginModalTemplate,
        parent: angular.element(document.body),
        clickOutsideToClose: false,
      };

      loginModal.show();
      expect($mdDialog.show).toHaveBeenCalledWith(dialogOptions);
    });
  });
});
