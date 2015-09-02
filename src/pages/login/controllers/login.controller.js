/**
 * login page main controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {authorisation} authorisation
 */
export default function loginCtrl($scope, $state, authorisation) {

  'ngInject';

  var vm = this;

  /**
   * user form submit action
   */
  vm.submit = async() =>  {
    delete vm.hasError;
    try {
      await authorisation.authorise(vm.email, vm.password);
      // if authorisation is success then redirect user to home page
      $state.go('msl.home');
    } catch (e) {
      vm.hasError = true;
      $scope.$evalAsync();
    }
  };
}


