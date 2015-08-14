import ArtistInfoEntity from "../entities/ArtistInfoEntity";

function artistStore (request, entityMapper) {
  "ngInject";

  const API_REQUEST_PATH = "/api/catalogedge/artist/";
  return {
    /**
     * @return {ArtistInfoEntity}
     */
    async fetch(artistId) {
      return entityMapper(await request.get(API_REQUEST_PATH + artistId), ArtistInfoEntity);
    }
  };
}

export default artistStore;
