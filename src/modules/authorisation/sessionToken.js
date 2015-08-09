function sessionToken (storage) {
  "ngInject";

  return {
    set(token) {
      storage.put("sessionId", token);
      this.token = token;
    },
    get() {
      return storage.get("sessionId");
    },
    has() {
      return !!storage.get("sessionId");
    },
    destroy() {
      storage.remove("sessionId");
    }
  };
}

export default sessionToken;
