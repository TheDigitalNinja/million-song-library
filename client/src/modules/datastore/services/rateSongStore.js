import {RATINGS_EDGE} from '../../../constants.js';

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

  const API_REQUEST_PATH = `${RATINGS_EDGE}ratesong/`;
  return {
    /**
     * make rate song request
     * @name rateSongStore#push
     * @param {string} songId
     * @param {number} rating
     * @return {StatusResponseEntity}
     */
    async push(songId, rating) {
      const data = `rating=${ rating }`;
      const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };

      const response = await request.put(API_REQUEST_PATH + songId, data, headers);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default rateSongStore;
