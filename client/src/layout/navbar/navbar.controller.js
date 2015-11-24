/**
 * navbar controller
 * it should only process authentication and navigation actions only
 */
export default class navbarCtrl {

  /*@ngInject*/

  /**
   * Class constructor
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {msl.authentication} authentication
   */
  constructor($scope, $state, authentication) {
    this.$scope = $scope;
    this.$state = $state;
    this.authentication = authentication;
  }

  /**
   * logout action for destroying session
   * after logout redirect user to login page
   */
  async logout() {
    await this.authentication.destroy();
    if(this.$state.is('msl.library')) {
      this.$state.go('msl.home');
    }
    else {
      this.$state.go('msl.login');
    }
  }

}
