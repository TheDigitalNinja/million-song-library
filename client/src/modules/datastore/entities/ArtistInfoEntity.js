/**
 * @name ArtistInfoEntity
 * @property {String} artistId
 * @property {String} artistMbid
 * @property {String} artistName
 * @property {String} albumsList
 * @property {String} favoritesTimestamp
 * @property {Number} averageRating
 * @property {Number} personalRating
 * @property {String} imageLink
 * @property {String} songsList
 * @property {String} similarArtistsList
 * @property {Boolean} inMyLibrary
 */
class ArtistInfoEntity {
  constructor() {
    this.artistId = String;
    this.artistMbid = String;
    this.artistName = String;
    this.albumsList = [String];
    this.favoritesTimestamp = String;
    this.averageRating = Number;
    this.personalRating = Number;
    this.imageLink = String;
    this.songsList = [String];
    this.similarArtistsList = [String];
    this.inMyLibrary = Boolean;
  }
}

export default ArtistInfoEntity;
