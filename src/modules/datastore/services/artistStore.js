import ArtistInfoEntity from '../entities/ArtistInfoEntity';
import ArtistListEntity from '../entities/ArtistListEntity';
import AlbumListEntity from '../entities/AlbumListEntity';

/**
 * artist store
 * @param {request} request
 * @param {entityMapper} entityMapper
 * @returns {*}
 */
export default function artistStore(request, entityMapper) {
  'ngInject';

  const API_REQUEST_PATH = '/api/catalogedge/artists/';
  return {
    /**
     * fetch artist from catalogue endpoint
     * @name artistStore#fetch
     * @param {string} artistId
     * @return {ArtistInfoEntity}
     */
    async fetch(artistId) {
      return entityMapper(await request.get(API_REQUEST_PATH + artistId), ArtistInfoEntity);
    },
    async fetchAll(){
      return entityMapper(await request.get(API_REQUEST_PATH), ArtistListEntity);
    },
    async fetchArtistAlbums(artistId) {
      return entityMapper(await request.get(API_REQUEST_PATH + artistId + '/albums'), AlbumListEntity);
    },
  };
}
