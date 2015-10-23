import CatalogListEntity from '../entities/CatalogListEntity';

/**
 * catalog store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
function catalogStore (request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/v1/catalogedge/browse/song';
  return {
    /**
     * fetch songs from catalogue endpoint
     * @name catalogStore#fetch
     * @param {array} opts
     * @return {CatalogListEntity}
     */
    async fetch(opts) {
      return entityMapper(await request.get(API_REQUEST_PATH, { params: { facets: JSON.stringify(opts) }}), CatalogListEntity);
    },
  };
}

export default catalogStore;
