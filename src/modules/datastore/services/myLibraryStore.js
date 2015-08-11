import MyLibraryEntity from "../entities/MyLibraryEntity";

function myLibraryStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/accountedge/users/mylibrary";
  return {
    /**
     * @return {MyLibraryEntity}
     */
    async fetch() {
      return entityMapper(await request.get(API_REQUEST_PATH), MyLibraryEntity);
    }
  };
}

export default myLibraryStore;
