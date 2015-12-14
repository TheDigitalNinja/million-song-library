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
  constructor(songModel, albumModel, artistModel, $location) {
    this.songModel = songModel;
    this.albumModel = albumModel;
    this.artistModel = artistModel;
    this.$location = $location;

    this.selectedGenreId = null;
    this.selectedGenreName = null;
    this.selectedRating = null;
    this.listener = null;
  }

  /**
   * Sets the selected genre
   * @param {object} genre
   */
  setSelectedGenre(genre) {
    if(genre) {
      this.selectedGenreId = genre.facetId;
      this.selectedGenreName = genre.name;
    }
    else {
      this.selectedGenreId = null;
      this.selectedGenreName = null;
    }
    this.selectedRating = null;
  }

  /**
   * Sets the selected rating
   * @param {string} ratingId
   */
  setSelectedRating(ratingId) {
    this.selectedRating = ratingId;
    this.selectedGenreId = null;
    this.selectedGenreName = null;
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
   * Gets the current location search params and applies the filters
   * @param {Object} listener
   */
  applyCurrentFilters(listener) {
    const currentGenre = this.$location.search().genre;
    const currentRating = this.$location.search().rating;

    if(currentRating) {
      this.setSelectedRating(currentRating);
    }
    if(currentGenre) {
      this.setSelectedGenre(currentGenre);
    }
    this.filter(listener);
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
    const genre = this.selectedGenreId;
    const facets = _.filter([rating, genre], (facet) => facet != null).join();
    return facets.length > 0 ? facets : undefined;
  }
}
