/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$rootScope.Scope} $scope
   * @param {$log} $log
   * @param {albumModel} albumModel
   * @param {artistModel} artistModel
   * @param {songModel} songModel
   */
  constructor($scope,
              $log,
              albumModel,
              artistModel,
              songModel,
              filterModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistModel = artistModel;
    this.albumModel = albumModel;
    this.songModel = songModel;
    this.activeTab = 'songs';

    filterModel.applyCurrentFilters(this);
  }

  /**
   * Called when a filter is applied to the songs
   * @param {SongInfoEntity[]} songs
   */
  songsFiltered(songs) {
    this.songModel.songs = songs;
    this.$scope.$evalAsync();
  }

  /**
   * Called when a filter is applied to the albums
   * @param {AlbumInfoEntity[]} albums
   */
  albumsFiltered(albums) {
    this.albumModel.albums = albums;
    this.$scope.$evalAsync();
  }

  /**
   * Called when a filter is applied to the artists
   * @param {ArtistInfoEntity[]} artists
   */
  artistsFiltered(artists) {
    this.artistModel.artists = artists;
    this.$scope.$evalAsync();
  }
}

