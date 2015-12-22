/**
 * Artist page controller
 */

export default class artistCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @param {artistModel} artistModel
   * @param {$rootScope.Scope} $scope
   * @param {ui.router.state.$stateParams} $stateParams
   * @param {ui.router.state.$state} $state
   * @param {authentication} authentication
   */
  constructor(artistModel, $scope, $stateParams, $state, authentication) {
    if(angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      this.$scope = $scope;
      this.model = artistModel;
      this.artistId = $stateParams.artistId;
      this.authentication = authentication;
      this.activeTab = 'songs';
      //Initialize data
      this.getArtist();
    }
    else {
      $state.go('msl.home');
    }
  }

  /**
   * Gets the artists his similarArtists and albums
   */
  getArtist() {
    this.model.getArtist(this.artistId, (artist) => {
      this.artist = artist;
      this.getSongs(artist);
      this.getAlbums(artist.albumsList);
      this.getSimilarArtists(artist.similarArtistsList);
    });
  }

  /**
   * Gets the similar artists from a list of artist ids
   * @param {String[]} similarArtistsList
   */
  getSimilarArtists(similarArtistsList) {
    this.model.getArtistsById(similarArtistsList, (artists) => {
      this.similarArtists = artists;
    });
  }

  /**
   * Gets albums from a list of album ids
   * @param {String[]} albumsList
   */
  getAlbums(albumsList) {
    this.model.getArtistAlbums(albumsList, (albums) => {
      this.artistAlbums = albums;
    });
  }

  /**
   * Gets songs from the artist
   * @param {ArtistInfoEntity} artist
   */
  getSongs(artist) {
    this.model.getArtistSongs(artist, (songs) => {
      this.songs = songs;
    });
  }
}

