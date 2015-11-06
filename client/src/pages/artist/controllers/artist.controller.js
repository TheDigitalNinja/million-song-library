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
   */
  constructor(artistModel, $scope, $stateParams, $state) {
    if(angular.isDefined($stateParams.artistId) && $stateParams.artistId.length > 0) {
      this.artistId = $stateParams.artistId;
      this.model = artistModel;
      this.$scope = $scope;
      this.activeTab = 'songs';
      //Initialize data
      this.displaySongs = true;

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

