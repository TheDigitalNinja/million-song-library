/**
 * @name AlbumInfoEntity
 * @property {String} albumId
 * @property {String} albumName
 * @property {String} artistId
 * @property {String} artistName
 * @property {String} genre
 * @property {Number} year
 * @property {Number} averageRating
 * @property {Number} personalRating
 * @property {String} imageLink
 * @property {String []} songsList
 */
class AlbumInfoEntity {
  constructor() {
    this.albumId = String;
    this.albumName = String;
    this.artistId = String;
    this.artistName = String;
    this.genre = String;
    this.year = Number;
    this.averageRating = Number;
    this.personalRating = Number;
    this.imageLink = String;
    this.songsList = [String];
  }
}

export default AlbumInfoEntity;
