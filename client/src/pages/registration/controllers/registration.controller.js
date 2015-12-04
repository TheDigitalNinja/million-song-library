/**
 * Registration page main controller
 * @param {ui.router.state.$state} $state
 */
export default class registrationCtrl {
  /*@ngInject*/

  constructor($state) {
    this.$state = $state;
  }

  submit() {
    this.$state.go('msl.home');
  }
}
