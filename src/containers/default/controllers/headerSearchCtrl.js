/**
 * header search controller
 * it should only process input search field
 * @param {ui.router.state.$state} $state
 */
function headerSearchCtrl ($state) {
  "ngInject";

  this.submitForm = () => {
    $state.go("default.search", {query: this.q});
  };
}

export default headerSearchCtrl;
