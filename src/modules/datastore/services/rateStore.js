import StatusResponseEntity from '../entities/StatusResponseEntity';

/**
 * rate store service
 * @name rateStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function rateStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/ratingsedge/ratesong/';
  return {
    /**
     * make rate song request
     * @name rateStore#push
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

export default rateStore;
