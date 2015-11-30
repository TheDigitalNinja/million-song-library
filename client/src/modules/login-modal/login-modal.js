import loginModalTemplate from './login-modal.html';

export default class LoginModal {
  /*@ngInject*/

  constructor($mdDialog, loginModalCtrl) {
    this.$mdDialog = $mdDialog;
    this.loginModalCtrl = loginModalCtrl;
  }

  show() {
    this.$mdDialog.show({
      controller: this.loginModalCtrl,
      controllerAs: 'vm',
      template: loginModalTemplate,
      parent: angular.element(document.body),
      clickOutsideToClose: false,
    });
  }
}
