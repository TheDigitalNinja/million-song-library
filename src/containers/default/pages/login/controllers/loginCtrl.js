function loginCtrl ($state, authorisation) {
  "ngInject";

  this.submit = async () => {
    await authorisation.authorise({login: this.email, password: this.password});
    $state.go("default.home");
  };
}

export default loginCtrl;
