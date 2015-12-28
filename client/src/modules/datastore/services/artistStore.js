import {PAGE_SIZE} from '../../../constants.js';
import {CATALOG_EDGE} from '../../../constants.js';

/**
 * artist store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {ArtistInfoEntity} ArtistInfoEntity
 * @param {ArtistListEntity} ArtistListEntity
 * @param {$log} $log
 * @returns {*}
 */

export default function artistStore(request,
                                    entityMapper,
                                    ArtistInfoEntity,
                                    ArtistListEntity,
                                    $log) {
  'ngInject';

  const API_REQUEST_PATH = CATALOG_EDGE;
  return {
    /**
     * fetch artist from catalogue endpoint
     * @name artistStore#fetch
     * @param {string} artistId
     * @return {ArtistInfoEntity}
     */
    async fetch(artistId) {
      try {
        const response = await request.get(`${ API_REQUEST_PATH }artist/${ artistId }`);
        return entityMapper(response.data, ArtistInfoEntity);
      } catch(error) {
        $log.error(error);
      }

    },

    /**
     * fetch all artists from catalogue endpoint
     * @name artistStore#fetchAll
     * @param {string} facets - comma separated list of facetIds
     * @return {ArtistListEntity}
     */
    async fetchAll(facets) {
      try {
        const params = { params: { items: PAGE_SIZE, facets: facets } };
        const response = await request.get(`${ API_REQUEST_PATH }browse/artist`, params);
        return entityMapper(response.data, ArtistListEntity);
      } catch(error) {
        $log.error(error);
      }
    },
  };
}
