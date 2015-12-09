/**
 * @name AlbumInfoEntity
 * @property {String} albumId
 * @property {String} albumName
 * @property {String} artistId
 * @property {String} artistMbid
 * @property {String} artistName
 * @property {String} genre
 * @property {Number} year
 * @property {String} favoritesTimestamp
 * @property {Number} averageRating
 * @property {Number} personalRating
 * @property {String} imageLink
 * @property {String[]} songsList
 * @property {Boolean} inMyLibrary
 */
class AlbumInfoEntity {
  constructor() {
    this.albumId = String;
    this.albumName = String;
    this.artistId = String;
    this.artistMbid = String;
    this.artistName = String;
    this.genre = String;
    this.year = Number;
    this.favoritesTimestamp = String;
    this.averageRating = Number;
    this.personalRating = Number;
    this.imageLink = String;
    this.songsList = [String];
    this.inMyLibrary = Boolean;
  }
}

export default AlbumInfoEntity;
