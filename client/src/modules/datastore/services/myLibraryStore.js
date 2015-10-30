import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * my library store service
 * @name myLibraryStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function myLibraryStore(request, entityMapper, SongListEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/accountedge/users/mylibrary';
  return {
    /**
     * fetch songs from account library endpoint
     * @name myLibraryStore#fetch
     * @return {SongListEntity}
     */
    async fetch() {
      const response = await request.get(API_REQUEST_PATH);
      return entityMapper(response, SongListEntity);
    },

    // TODO: Implement add Song/Album/Artist to Library
  };
}

export default myLibraryStore;
