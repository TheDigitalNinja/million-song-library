/**
 * login page main controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {authorisation} authorisation
 */
function loginCtrl ($scope, $state, authorisation) {
  "ngInject";

  /**
   * user form submit action
   */
  this.submit = async () => {
    delete this.hasError;
    try {
      await authorisation.authorise(this.email, this.password);
      // if authorisation is success then redirect user to home page
      $state.go("default.home");
    } catch (e) {
      this.hasError = true;
      $scope.$evalAsync();
    }
  };
}

export default loginCtrl;
