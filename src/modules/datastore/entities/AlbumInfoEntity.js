/**
 * @name AlbumInfoEntity
 * @property {string} albumId
 * @property {String} albumName
 * @property {String} linkToImage
 * @property {string} songsList
 * @property {string} albumYear
 */
export default class AlbumInfoEntity {
  constructor() {
    this.albumId = String;
    this.albumName = String;
    this.linkToImage = String;
    this.songsList = String;
    this.albumYear = String;
  }
}
