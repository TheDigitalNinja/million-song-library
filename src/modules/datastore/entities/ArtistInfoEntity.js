/**
 * @name ArtistInfoEntity
 * @property {String} artistId
 * @property {String} artistName
 * @property {String} albumsList
 * @property {Number} averageRating
 * @property {Number} personalRating
 * @property {String} imageLink
 * @property {String} songsList
 * @property {String} similarArtistsList
 */
class ArtistInfoEntity {
  constructor() {
    this.artistId = String;
    this.artistName = String;
    this.albumsList = String;
    this.averageRating = Number;
    this.personalRating = Number;
    this.imageLink = String;
    this.songsList = [String];
    this.similarArtistsList = [String];
  }
}

export default ArtistInfoEntity;
