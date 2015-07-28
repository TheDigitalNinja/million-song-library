function loginCtrl ($state, authorisation) {
  "ngInject";

  this.loginWithGoogle = () => {
    authorisation.authorise();
    $state.go("default.home");
  };

  this.loginWithFacebook = () => {
    authorisation.authorise();
    $state.go("default.home");
  };
}

export default loginCtrl;
