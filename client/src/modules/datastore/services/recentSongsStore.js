import {ACCOUNT_EDGE} from '../../../constants.js';

/**
 * recent songs store service
 * @name recentSongsStore
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @param {SongListEntity} SongListEntity
 * @returns {*}
 */
function recentSongsStore (request, entityMapper, SongListEntity) {
  'ngInject';

  const API_REQUEST_PATH = `${ACCOUNT_EDGE}users/recentsongs`;
  return {
    /**
     * fetch songs list from account recent songs endpoint
     * @name recentSongsStore#fetch
     * @return {SongListEntity}
     */
    async fetch() {
      const response = await request.get(API_REQUEST_PATH);
      return entityMapper(response, SongListEntity);
    },
  };
}

export default recentSongsStore;
