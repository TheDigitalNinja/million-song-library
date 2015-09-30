import SongListEntity from '../entities/SongListEntity';
import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * my library store service
 * @name myLibraryStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function myLibraryStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/accountedge/users/mylibrary';
  return {
    /**
     * fetch songs from account library endpoint
     * @name myLibraryStore#fetch
     * @return {SongListEntity}
     */
    async fetch() {
      return entityMapper(await request.get(API_REQUEST_PATH), SongListEntity);
    },

    async addSong(songId) {
      return entityMapper(await request.post(API_REQUEST_PATH + '/add', { songId: songId }), StatusResponseEntity);
    },
  };
}

export default myLibraryStore;
