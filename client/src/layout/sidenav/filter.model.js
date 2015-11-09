import _ from 'lodash';
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
   * @property {string} selectedRating - rating to filter catalog
   * @property {string} selectedGenre  - genre to filter catalog
   * @property {object} listener       - object who receive the filter messages
   */
  constructor(songModel, albumModel, artistModel) {
    this.songModel = songModel;
    this.albumModel = albumModel;
    this.artistModel = artistModel;

    this.selectedGenre = null;
    this.selectedRating = null;
    this.listener = null;
  }

  /**
   * Sets the selected genre
   * @param {string} genreId
   */
  setSelectedGenre(genreId) {
    this.selectedGenre = genreId;
    this.selectedRating = null;
  }

  /**
   * Sets the selected rating
   * @param {string} ratingId
   */
  setSelectedRating(ratingId) {
    this.selectedRating = ratingId;
    this.selectedGenre = null;
  }

  /**
   * Applies the filters on songs albums and artists
   * @param {Object} listener
   */
  filter(listener) {
    this.listener = listener;

    this._filterSongs();
    this._filterAlbums();
    this._filterArtists();
  }

  /**
   * Applies the filters on the songs catalog
   * @private
   */
  _filterSongs() {
    this.songModel.filterSongs(this._getFacets(), (songs) => {
      if(this.listener && this.listener.songsFiltered) {
        this.listener.songsFiltered(songs);
      }
    });
  }

  /**
   * Applies the filters on the albums catalog
   * @private
   */
  _filterAlbums() {
    this.albumModel.filterAlbums(this._getFacets(), (albums) => {
      if(this.listener && this.listener.albumsFiltered) {
        this.listener.albumsFiltered(albums);
      }
    });
  }

  /**
   * Applies the filters on the artists catalog
   * @private
   */
  _filterArtists() {
    this.artistModel.filterArtists(this._getFacets(), (artists) => {
      if(this.listener && this.listener.artistsFiltered) {
        this.listener.artistsFiltered(artists);
      }
    });
  }

  /**
   * Retrieves the facets in string format
   * @returns {string}
   * @private
   */
  _getFacets() {
    const rating = this.selectedRating;
    const genre = this.selectedGenre;
    const facets = _.filter([rating, genre], (facet) => facet != null).join();
    return facets.length > 0 ? facets : undefined;
  }

}
