/**
 * @name SongInfoEntity
 * @property {string} artistMbid
 * @property {string} artistName
 * @property {number} averageRating
 * @property {number} danceability
 * @property {number} duration
 * @property {string} linkToImage
 * @property {string} release
 * @property {number} songHotttnesss
 * @property {string} songId
 * @property {string} songName
 * @property {string} genreName
 */
class SongInfoEntity {
  constructor() {
    this.artistMbid = String;
    this.artistName = String;
    this.averageRating = Number;
    this.danceability = Number;
    this.duration = Number;
    this.linkToImage = String;
    this.release = String;
    this.songHotttnesss = Number;
    this.songId = String;
    this.songName = String;
    this.genreName = String;
  }
}

export default SongInfoEntity;
