function headerSearchCtrl ($scope, $state) {
  "ngInject";

  this.submitForm = () => {
    $state.go("default.search", {query: this.q});
  };
}

export default headerSearchCtrl;
