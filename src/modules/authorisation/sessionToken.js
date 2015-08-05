function sessionToken () {
  "ngInject";

  return {
    set(token) {
      this.token = token;
    },
    get() {
      return this.token;
    },
    destroy() {
      delete this.token;
    }
  };
}

export default sessionToken;
