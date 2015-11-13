/**
 * rate song store service
 * @name rateSongStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {StatusResponseEntity} StatusResponseEntity
 * @returns {*}
 */
function rateSongStore (request, entityMapper, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/ratingsedge/ratesong/';
  return {
    /**
     * make rate song request
     * @name rateSongStore#push
     * @param {string} songId
     * @param {number} rating
     * @return {StatusResponseEntity}
     */
    async push(songId, rating) {
      const response = await request.put(API_REQUEST_PATH + songId, { rating });
      return entityMapper(response, StatusResponseEntity);
    },
  };
}

export default rateSongStore;
