/**
 * navbar controller
 * it should only process authentication and navigation actions only
 */
export default class navbarCtrl {

  /*@ngInject*/

  /**
   * Class constructor
   * @param $scope
   * @param $state
   * @param authorisation
   */
  constructor($scope, $state, authorisation) {

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
      } else {
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
