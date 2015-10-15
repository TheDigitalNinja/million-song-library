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
  constructor($rootScope, $scope, $state, authorisation) {

    /**
     * Check and see if we are on the home page so the template
     * knows whether to show the large or small hero.
     */

    var vm = this;

    vm.isHome = isHomeCheck($state.current.name);

    $rootScope.$on('$stateChangeStart', function(event, toState){
      vm.isHome = isHomeCheck(toState.name);
    });

    function isHomeCheck(newState) {
      if (newState === 'msl.home') {
        return true;
      }
      return false;
    }

    /**
     * on authorisation state changes we what to catch that event
     * and update controller authenticated user data
     * vm event is not triggered by angular so we must manually
     * trigger scope change
     */

    this.onStateChange = () => {
      this.authorised = authorisation.isAuthorised();
      if (this.authorised) {
        this.email = authorisation.getUserData('userEmail');
      }
      else {
        delete this.email;
      }
      $scope.$evalAsync();
    };

    /**
     * logout action for destroying session
     * after logout redirect user to login page
     */
    this.logout = async() => {
      await authorisation.destroy();
      $state.go('msl.login');
    };

    // add authorisation state change listener
    authorisation.addChangeListener(this.onStateChange);
    // when scope destroys remove authorisation state change listener
    $scope.$on('$destroy', () => authorisation.removeChangeListener(this.onStateChange));
  }

}
