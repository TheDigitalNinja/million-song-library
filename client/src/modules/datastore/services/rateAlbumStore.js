import {RATINGS_EDGE} from '../../../constants.js';

/**
 * rate album store service
 * @name rateAlbumStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {StatusResponseEntity} StatusResponseEntity
 * @returns {*}
 */
function rateAlbumStore (request, entityMapper, StatusResponseEntity) {
  'ngInject';

  const API_REQUEST_PATH = `${RATINGS_EDGE}ratealbum/`;
  return {
    /**
     * make rate album request
     * @name rateAlbumStore#push
     * @param {string} albumId
     * @param {number} rating
     * @return {StatusResponseEntity}
     */
    async push(albumId, rating) {
      const data = `rating=${ rating }`;
      const headers = { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } };

      const response = await request.put(API_REQUEST_PATH + albumId, data, headers);
      return entityMapper(response.data, StatusResponseEntity);
    },
  };
}

export default rateAlbumStore;
