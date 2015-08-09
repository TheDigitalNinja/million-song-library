function headerCtrl ($scope, $state, authorisation) {
  "ngInject";

  var onStateChange = () => {
    this.authorised = authorisation.isAuthorised();
    if (this.authorised) {
      this.email = authorisation.getUserData("userEmail");
    } else {
      delete this.email;
    }
    $scope.$evalAsync();
  };

  this.logout = async () => {
    await authorisation.destroy();
    $state.go("default.login");
  };

  authorisation.addChangeListener(onStateChange);
  $scope.$on("$destroy", () => authorisation.removeChangeListener(onStateChange));
}

export default headerCtrl;
