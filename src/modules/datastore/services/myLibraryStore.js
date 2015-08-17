import SongListEntity from "../entities/SongListEntity";

/**
 * my library store service
 * @name myLibraryStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function myLibraryStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/accountedge/users/mylibrary";
  return {
    /**
     * fetch songs from account library endpoint
     * @name myLibraryStore#fetch
     * @return {SongListEntity}
     */
    async fetch() {
      return entityMapper(await request.get(API_REQUEST_PATH), SongListEntity);
    }
  };
}

export default myLibraryStore;
