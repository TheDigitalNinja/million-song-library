/**
 * artist store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {ArtistInfoEntity} ArtistInfoEntity
 * @param {ArtistListEntity} ArtistListEntity
 * @param {$log} $log
 * @returns {*}
 */
function artistStore(request, entityMapper, ArtistInfoEntity, ArtistListEntity, $log) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/catalogedge/';
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
     * @param {string} genre
     * @return {ArtistListEntity}
     */
    async fetchAll(genre) {
      try {
        const params = { params: { facets: genre } };
        const response = await request.get(`${ API_REQUEST_PATH }browse/artist`, params);
        return entityMapper(response.data, ArtistListEntity);
      } catch(error) {
        $log.error(error);
      }
    },
  };
}

export default artistStore;
