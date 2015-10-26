/**
 * navbar search controller
 * it should only process input search field
 * @param {ui.router.state.$state} $state
 */
export default class navbarSearchCtrl {
  /*@ngInject*/

  constructor($state) {
    this.$state = $state;
  }

  submitForm() {
    this.$state.go('search', { query: this.q });
  }
}

