import {RATINGS_EDGE} from '../../../constants.js';

/**
 * rate artist store service
 * @name rateArtistStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {StatusResponseEntity} StatusResponseEntity
 * @returns {*}
 */
function rateArtistStore (request, entityMapper, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = `${RATINGS_EDGE}rateartist/`;
  return {
    /**
     * make rate artist request
     * @name rateArtistStore#push
     * @param {string} artistId
     * @param {number} rating
     * @return {StatusResponseEntity}
     */
    async push(artistId, rating) {
      const data = `rating=${ rating }`;
      const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };

      const response = await request.put(API_REQUEST_PATH + artistId, data, headers);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default rateArtistStore;
