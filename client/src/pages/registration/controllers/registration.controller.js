/**
 * Registration page main controller
 * login page main controller
 * @param {$rootScope.Scope} $scope
 * @param {authentication} authentication
 * @param {registrationStore} registrationStore
 * @param {ui.router.state.$state} $state
 */
export default class registrationCtrl {
  /*@ngInject*/

  constructor($scope, authentication, registrationStore, $state) {
    this.$scope = $scope;
    this.authentication = authentication;
    this.registrationStore = registrationStore;
    this.$state = $state;
  }

  async submit() {
    delete this.hasError;
    try {
      await this.registrationStore.registration(this.email,
        this.password, this.confirmationPassword);
      await this.authentication.authenticate(this.email, this.password);
      // if login is success then redirect user to home page
      this.$state.go('msl.home');
    } catch(e) {
      this.hasError = true;
      this.$scope.$evalAsync();
    }
  }
}
