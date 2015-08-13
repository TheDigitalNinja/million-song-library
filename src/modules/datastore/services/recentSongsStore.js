import SongListEntity from "../entities/SongListEntity";

function recentSongsStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/accountedge/users/recentsongs";
  return {
    /**
     * @return {SongListEntity}
     */
    async fetch() {
      return entityMapper(await request.get(API_REQUEST_PATH), SongListEntity);
    }
  };
}

export default recentSongsStore;
