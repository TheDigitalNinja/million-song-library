import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * my library store service
 * @name myLibraryStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {MyLibraryEntity} MyLibraryEntity
 * @param {StatusResponseEntity} StatusResponseEntity
 * @param {$log} $log
 * @param {$rootScope} $rootScope
 * @returns {*}
 */
function myLibraryStore(
  request,
  entityMapper,
  MyLibraryEntity,
  StatusResponseEntity,
  $log,
  $rootScope
) {
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
      if(response.message === 'success') {
        $rootScope.$emit('addedToLibrary', 'Song');
        //TODO replace for toastr
        $log.info('Successfully added song to library');
      }
      else {
        //TODO show toastr
        $log.error('Unable to add song to library');
      }
      return entityMapper(response, StatusResponseEntity);
    },

    /**
     * Add album to my library
     * @param {string} albumId
     * @return {StatusResponseEntity}
     */
    async addAlbum(albumId) {
      const apiPath = `${ API_REQUEST_PATH }/addalbum/${ albumId }`;
      const response = await request.put(apiPath);
      if(response.message === 'success') {
        $rootScope.$emit('addedToLibrary', 'Album');
        //TODO replace for toastr
        $log.info('Successfully added album to library');
      }
      else {
        //TODO show toastr
        $log.error('Unable to add album to library');
      }
      return entityMapper(response, StatusResponseEntity);
    },

    /**
     * Add artist to my library
     * @param {string} artistId
     * @return {StatusResponseEntity}
     */
    async addArtist(artistId) {
      const apiPath = `${ API_REQUEST_PATH }/addartist/${ artistId }`;
      const response = await request.put(apiPath);
      if(response.message === 'success') {
        $rootScope.$emit('addedToLibrary', 'Artist');
        //TODO replace for toastr
        $log.info('Successfully added artist');
      }
      else {
        //TODO show toastr
        $log.error('Unable to add artist to library');
      }
      return entityMapper(response, StatusResponseEntity);
    },

    /**
     * Remove song to my library
     * @param {string} songId
     * @param {string} timestamp
     * @return {StatusResponseEntity}
     */
    async removeSong(songId, timestamp) {
      const apiPath = `${ API_REQUEST_PATH }/removesong/${ songId }/${ timestamp }`;
      const response = await request.delete(apiPath);
      if(response.message === 'success') {
        $rootScope.$emit('deletedFromLibrary', 'Song');
        //TODO replace for toastr
        $log.info('Successfully deleted song');
      }
      else {
        //TODO show toastr
        $log.error('Unable to delete song');
      }
      return entityMapper(response, StatusResponseEntity);
    },

    /**
     * Remove album to my library
     * @param {string} albumId
     * @param {string} timestamp
     * @return {StatusResponseEntity}
     */
    async removeAlbum(albumId, timestamp) {
      const apiPath = `${ API_REQUEST_PATH }/removealbum/${ albumId }/${ timestamp }`;
      const response = await request.delete(apiPath);
      if(response.message === 'success') {
        $rootScope.$emit('deletedFromLibrary', 'Album');
        //TODO replace for toastr
        $log.info('Successfully deleted album');
      }
      else {
        //TODO show toastr
        $log.error('Unable to delete album');
      }
      return entityMapper(response, StatusResponseEntity);
    },

    /**
     * Remove artist to my library
     * @param {string} artistId
     * @param {string} timestamp
     * @return {StatusResponseEntity}
     */
    async removeArtist(artistId, timestamp) {
      const apiPath = `${ API_REQUEST_PATH }/removeartist/${ artistId }/${ timestamp }`;
      const response = await request.delete(apiPath);
      if(response.message === 'success') {
        $rootScope.$emit('deletedFromLibrary', 'Artist');
        //TODO replace for toastr
        $log.info('Successfully deleted artist');
      }
      else {
        //TODO show toastr
        $log.error('Unable to delete artist');
      }
      return entityMapper(response, StatusResponseEntity);
    },
  };
}

export default myLibraryStore;
