import SongInfoEntity from './SongInfoEntity';
/**
 * @name SongListEntity
 * @property {string} genre
 * @property {SongInfoEntity[]} songs
 */
class SongListEntity {
  constructor() {
    this.genre = String;
    this.songs = [SongInfoEntity];
  }
}

export default SongListEntity;
