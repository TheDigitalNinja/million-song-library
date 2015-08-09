function loginCtrl ($scope, $state, authorisation) {
  "ngInject";

  this.submit = async () => {
    delete this.hasError;
    try {
      await authorisation.authorise({login: this.email, password: this.password});
      $state.go("default.home");
    } catch (e) {
      this.hasError = true;
      $scope.$evalAsync();
    }
  };
}

export default loginCtrl;
