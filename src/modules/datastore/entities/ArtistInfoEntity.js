/**
 * @name ArtistInfoEntity
 * @property {string} albumsList
 * @property {string} artistId
 * @property {String} artistMbid
 * @property {String} artistName
 * @property {String} linkToImage
 * @property {string} songsList
 */
class ArtistInfoEntity {
  constructor() {
    this.albumsList = String;
    this.artistId = String;
    this.artistMbid = String;
    this.artistName = String;
    this.linkToImage = String;
    this.songsList = String;
  }
}

export default ArtistInfoEntity;
