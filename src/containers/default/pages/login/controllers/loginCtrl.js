function loginCtrl ($state, authorisation) {
  "ngInject";

  this.loginWithGoogle = () => {
    authorisation.authorise(true);
    $state.go("default.home");
  };

  this.loginWithFacebook = () => {
    authorisation.authorise(true);
    $state.go("default.home");
  };
}

export default loginCtrl;
