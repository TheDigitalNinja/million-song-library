import ArtistInfoEntity from "../entities/ArtistInfoEntity";

/**
 * artist store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function artistStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/catalogedge/artist/";
  return {
    /**
     * fetch artist from catalogue endpoint
     * @name artistStore#fetch
     * @param {string} artistId
     * @return {ArtistInfoEntity}
     */
    async fetch(artistId) {
      return entityMapper(await request.get(API_REQUEST_PATH + artistId), ArtistInfoEntity);
    }
  };
}

export default artistStore;
