/**
 * navbar controller
 * it should only process authentication and navigation actions only
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {authorisation} authorisation
 */
export default function navbarCtrl($scope, $state, authorisation) {

  'ngInject';

  var vm = this;

  /**
   * on authorisation state changes we what to catch that event
   * and update controller authenticated user data
   * vm event is not triggered by angular so we must manually
   * trigger scope change
   */
  var onStateChange = () => {
    vm.authorised = authorisation.isAuthorised();
    if (vm.authorised) {
      vm.email = authorisation.getUserData('userEmail');
    } else {
      delete vm.email;
    }
    $scope.$evalAsync();
  };

  /**
   * logout action for destroying session
   * after logout redirect user to login page
   */

  vm.logout = async() => {
  await authorisation.destroy();
    $state.go('msl.login');
  };

  function init () {
    // add authorisation state change listener
    authorisation.addChangeListener(onStateChange);
    // when scope destroys remove authorisation state change listener
    $scope.$on('$destroy', () => authorisation.removeChangeListener(onStateChange));
  }

  init();
}
