/**
 * navbar controller
 * it should only process authentication and navigation actions only
 */
export default class navbarCtrl {

  /*@ngInject*/

  /**
   * Class constructor
   * @param {$rootScope} $rootScope
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$state} $state
   * @param {msl.authorisation} authorisation
   */
  constructor($rootScope, $scope, $state, authorisation) {
    this.$scope = $scope;
    this.$state = $state;
    this.authorisation = authorisation;

    /**
     * Check and see if we are on the home page so the template
     * knows whether to show the large or small hero.
     */
    this.isHome = this.isHomeCheck($state.current.name);

    $rootScope.$on('$stateChangeStart', (event, toState) => {
      this.isHome = this.isHomeCheck(toState.name);
    });

    /**
     * on authorisation state changes we what to catch that event
     * and update controller authenticated user data
     * vm event is not triggered by angular so we must manually
     * trigger scope change
     */
    this.onStateChange = () => {
      this.authorised = this.authorisation.isAuthorised();
      if (this.authorised) {
        this.email = this.authorisation.getUserData('userEmail');
      }
      else {
        delete this.email;
      }
      this.$scope.$evalAsync();
    };

    // add authorisation state change listener
    this.authorisation.addChangeListener(this.onStateChange);
    // when scope destroys remove authorisation state change listener
    $scope.$on('$destroy', () => authorisation.removeChangeListener(this.onStateChange));
  }

  isHomeCheck(newState) {
    if (newState === 'msl.home') {
      return true;
    }
    return false;
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
