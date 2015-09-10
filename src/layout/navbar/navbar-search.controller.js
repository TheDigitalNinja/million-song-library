/**
 * navbar search controller
 * it should only process input search field
 * @param {ui.router.state.$state} $state
 */
export default function navbarSearchCtrl($state) {
  'ngInject';

  var vm = this;
  vm.submitForm = function submitForm() {
    $state.go('search', {query: this.q});
  };

}

