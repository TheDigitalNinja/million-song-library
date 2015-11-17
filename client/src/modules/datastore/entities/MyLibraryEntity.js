import AlbumInfoEntity from './AlbumInfoEntity';
import ArtistInfoEntity from './ArtistInfoEntity';
import SongInfoEntity from './SongInfoEntity';

/**
 * @name MyLibraryEntity
 * @property {AlbumInfoEntity} albums
 * @property {ArtistInfoEntity} artists
 * @property {SongInfoEntity} songs
 */
export default class MyLibraryEntity {
  constructor() {
    this.albums = [AlbumInfoEntity];
    this.artists = [ArtistInfoEntity];
    this.songs = [SongInfoEntity];
  }
}
