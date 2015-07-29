function storage ($cookies) {
  "ngInject";

  return {
    put(namespace, value) {
      $cookies.put(namespace, JSON.stringify(value));
    },
    get(namespace) {
      try {
        return JSON.parse($cookies.get(namespace));
      } catch (e) {
        return undefined;
      }
    },
    remove(namespace) {
      $cookies.remove(namespace);
    }
  };
}

export default storage;
