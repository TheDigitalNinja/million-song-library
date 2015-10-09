/**
 * @name AlbumInfoEntity
 * @property {string} albumId
 * @property {String} albumName
 * @property {String} linkToImage
 * @property {string} albumYear
 * @property {number} averageRating
 * @property {string} artistId
 * @property {string} artistName
 * @property {string} genreName
 * @property {string} songsList
 */
class AlbumInfoEntity {
  constructor() {
    this.albumId = String;
    this.albumName = String;
    this.linkToImage = String;
    this.albumYear = String;
    this.averageRating = Number;
    this.artistId = String;
    this.artistName = String;
    this.genreName = String;
    this.songsList = String;
  }
}

export default AlbumInfoEntity;
