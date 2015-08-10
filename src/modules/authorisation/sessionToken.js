function sessionToken ($cookies) {
  "ngInject";

  const STORAGE_NAMESPACE = "sessionId";

  return {
    set(token) {
      $cookies.put(STORAGE_NAMESPACE, token);
      return this;
    },
    get() {
      return $cookies.get(STORAGE_NAMESPACE);
    },
    has() {
      return !!$cookies.get(STORAGE_NAMESPACE);
    },
    destroy() {
      $cookies.remove(STORAGE_NAMESPACE);
      return this;
    }
  };
}

export default sessionToken;
