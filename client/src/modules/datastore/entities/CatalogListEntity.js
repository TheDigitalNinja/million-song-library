import SongInfoEntity from './SongInfoEntity';

/**
 * @name CatalogEntity
 * @property {string} genre
 * @property {SongInfoEntity[]} songs
 */
class CatalogEntity {
  constructor() {
    this.genre = String;
    this.songs = [SongInfoEntity];
  }
}

export default CatalogEntity;
