import SongInfoEntity from "../entities/SongInfoEntity";

function songStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/catalogedge/song/";
  return {
    /**
     * @return {SongInfoEntity}
     */
    async fetch(songId) {
      return entityMapper(await request.get(API_REQUEST_PATH + songId), SongInfoEntity);
    }
  };
}

export default songStore;
