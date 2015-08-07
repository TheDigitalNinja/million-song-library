function headerCtrl ($scope, $state, authorisation) {
  "ngInject";

  var onStateChange = () => {
    this.authorised = authorisation.isAuthorised();
    if (this.authorised) {
      this.email = authorisation.getUserData("email");
    } else {
      delete this.email;
    }
    $scope.$evalAsync();
  };

  this.logout = () => {
    authorisation.destroy();
    $state.go("default.login");
  };

  authorisation.addChangeListener(onStateChange);
  $scope.$on("$destroy", () => authorisation.removeChangeListener(onStateChange));
}

export default headerCtrl;
