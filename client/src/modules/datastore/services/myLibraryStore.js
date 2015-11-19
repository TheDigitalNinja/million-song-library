import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * my library store service
 * @name myLibraryStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function myLibraryStore(request, entityMapper, MyLibraryEntity, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/accountedge/users/mylibrary';
  return {
    /**
     * fetch my library from account endpoint
     * @name myLibraryStore#fetch
     * @return {MyLibraryEntity}
     */
    async fetch() {
      const response = await request.get(API_REQUEST_PATH);
      return entityMapper(response.data, MyLibraryEntity);
    },

    /**
     * Add song to my library
     * @param {string} songId
     * @return {StatusResponseEntity}
     */
    async addSong(songId) {
      const apiPath = `${ API_REQUEST_PATH }/addsong/${ songId }`;
      const response = await request.put(apiPath);
      return entityMapper(response.data, StatusResponseEntity);
    },

    /**
     * Add album to my library
     * @param {string} albumId
     * @return {StatusResponseEntity}
     */
    async addAlbum(albumId) {
      const apiPath = `${ API_REQUEST_PATH }/addalbum/${ albumId }`;
      const response = await request.put(apiPath);
      return entityMapper(response.data, StatusResponseEntity);
    },

     /**
     * Add artist to my library
     * @param {string} artistId
     * @return {StatusResponseEntity}
     */
    async addArtist(artistId) {
      const apiPath = `${ API_REQUEST_PATH }/addartist/${ artistId }`;
      const response = await request.put(apiPath);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default myLibraryStore;
