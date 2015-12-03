export default class loginModalCtrl {
  /*@ngInject*/

  constructor($scope, $mdDialog, authentication, $state) {
    this.$scope = $scope;
    this.$mdDialog = $mdDialog;
    this.authentication = authentication;
    this.$state = $state;
  }

  async submit() {
    delete this.hasError;
    try {
      await this.authentication.authenticate(this.email, this.password);

      this.$mdDialog.hide();
      this.$state.reload();
    } catch(e) {
      this.hasError = true;
      this.$scope.$evalAsync();
    }
  }

  cancel() {
    this.$mdDialog.cancel();
  }
}
