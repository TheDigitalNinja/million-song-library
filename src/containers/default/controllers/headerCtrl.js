function headerCtrl ($scope, $state, authorisation) {
  "ngInject";

  /**
   * on authorisation state change callback trigger
   */
  var onStateChange = () => {
    this.authorised = authorisation.isAuthorised();
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
