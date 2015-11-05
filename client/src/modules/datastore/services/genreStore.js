/**
 * genre store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {GenreListEntity} GenreListEntity
 * @returns {*}
 */
function genreStore(request, entityMapper, GenreListEntity) {
  'ngInject';

  const API_REQUEST_PATH = '/msl/v1/catalogedge/facet/';

  return {
    /**
     * fetch genres from catalogedge's facet endpoint
     * @param {string} genreName
     */
    async fetch(genreName = '~') {
      const response = await request.get(`${ API_REQUEST_PATH }${ genreName }`);
      return entityMapper(response, GenreListEntity);
    },
  };
}

export default genreStore;
