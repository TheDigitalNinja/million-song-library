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
   * @param {msl.authorisation} authorisation
   */
  constructor($scope, $state, authorisation) {
    this.$scope = $scope;
    this.$state = $state;
    this.authorisation = authorisation;
  }

  /**
   * logout action for destroying session
   * after logout redirect user to login page
   */
  async logout() {
    await this.authorisation.destroy();
    this.$state.go('msl.login');
  }

}
