/**
 * header controller
 * it should only process authentication and navigation actions only
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {authorisation} authorisation
 */
function headerCtrl ($scope, $state, authorisation) {
  "ngInject";

  /**
   * on authorisation state changes we what to catch that event
   * and update controller authenticated user data
   * this event is not triggered by angular so we must manually
   * trigger scope change
   */
  var onStateChange = () => {
    this.authorised = authorisation.isAuthorised();
    if (this.authorised) {
      this.email = authorisation.getUserData("userEmail");
    } else {
      delete this.email;
    }
    $scope.$evalAsync();
  };

  /**
   * logout action for destroying session
   * after logout redirect user to login page
   */
  this.logout = async () => {
    await authorisation.destroy();
    $state.go("default.login");
  };

  // add authorisation state change listener
  authorisation.addChangeListener(onStateChange);
  // when scope destroys remove authorisation state change listener
  $scope.$on("$destroy", () => authorisation.removeChangeListener(onStateChange));
}

export default headerCtrl;
