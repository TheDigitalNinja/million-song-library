function loginCtrl ($scope, $state, authorisation) {
  "ngInject";

  this.submit = async () => {
    delete this.hasError;
    try {
      await authorisation.authorise(this.email, this.password);
      $state.go("default.home");
    } catch (e) {
      this.hasError = true;
      $scope.$evalAsync();
    }
  };
}

export default loginCtrl;
