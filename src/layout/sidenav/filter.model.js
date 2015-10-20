/**
 * Filter Model
 * Filters the songs, artists and albums by genre and rating
 */
export default class filterModel {
  /*@ngInject*/

  /**
   * @constructor
   * @param {songModel} songModel
   * @param {albumModel} albumModel
   * @param {artistModel} artistModel
   * @property {number} minRating     - minimun rating to filter catalogs
   * @property {string} selectedGenre - genre to filter catalogs
   * @property {object} listener      - object who receive the filter messages
   */
  constructor(songModel, albumModel, artistModel) {
    this.songModel = songModel;
    this.albumModel = albumModel;
    this.artistModel = artistModel;

    this.minRating = null;
    this.selectedGenre = null;
    this.listener = null;
  }

  /**
   * Applies the filters on songs albums and artists
   * @param {{genre: string, rating: string}} filters
   * @param {Object} listener
   */
  filter(filters, listener) {
    this._applyFilters(filters);
    this.listener = listener;

    this.filterSongs();
    this.filterAlbums();
    this.filterArtists();
  }

  /**
   * Applies the filters on the songs catalog
   */
  filterSongs() {
    this.songModel.filterSongs(this.minRating, this.selectedGenre, (songs) => {
      if(this.listener && this.listener.songsFiltered) {
        this.listener.songsFiltered(songs);
      }
    });
  }

  /**
   * Applies the filters on the albums catalog
   */
  filterAlbums() {
    this.albumModel.filterAlbums(this.minRating, this.selectedGenre, (albums) => {
      if(this.listener && this.listener.albumsFiltered) {
        this.listener.albumsFiltered(albums);
      }
    });
  }

  /**
   * Applies the filters on the artists catalog
   */
  filterArtists() {
    this.artistModel.filterArtists(this.minRating, this.selectedGenre, (artists) => {
      if(this.listener && this.listener.artistsFiltered) {
        this.listener.artistsFiltered(artists);
      }
    });
  }

  /**
   * Processes the filter options
   * @private
   */
  _applyFilters(filters) {
    this.minRating = filters.rating === undefined ? this.minRating : filters.rating;
    this.selectedGenre = filters.genre === undefined ? this.selectedGenre : filters.genre;
  }
}
