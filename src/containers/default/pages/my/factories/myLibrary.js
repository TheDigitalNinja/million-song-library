function myLibrary (myLibraryStore) {
  "ngInject";

  return {
    async fetch() {
      var content = await myLibraryStore.fetch();
      console.log("response got", content);
      return content;
    }
  };
}

export default myLibrary;
