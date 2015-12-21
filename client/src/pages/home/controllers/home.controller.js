/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {$rootScope.Scope} $scope
   * @param {$location} $location
   * @param {$log} $log
   * @param {albumModel} albumModel
   * @param {artistModel} artistModel
   * @param {songModel} songModel
   * @param {filterModel} filterModel
   * @param {$rootScope} $rootScope
   */
  constructor($scope,
              $location,
              $log,
              albumModel,
              artistModel,
              songModel,
              filterModel,
              $rootScope) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistModel = artistModel;
    this.albumModel = albumModel;
    this.songModel = songModel;
    this.$location = $location;
    this.filterModel = filterModel;

    this._getCurrentTab();
    filterModel.applyCurrentFilters(this);

    //Update song album and artist list on removed from library
    $rootScope.$on('deletedFromLibrary', (event, data) => {
      filterModel.applyCurrentFilters(this);
    });

    //Update song album and artist list on added to library
    $rootScope.$on('addedToLibrary', (event, data) => {
      filterModel.applyCurrentFilters(this);
    });
  }

  /**
   * Sets the selected tab on the search params
   * @param {string} tab
   */
  selectTab(tab) {
    this.$location.search('tab', tab);
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
    this.isProcessing = false;
  }

  /**
   * Gets the current active tab from $location search
   * @private
   */
  _getCurrentTab() {
    const tabs = ['songs', 'albums', 'artists'];
    const tabLabel = this.$location.search().tab;
    const index = tabs.indexOf(tabLabel);
    this.selectedTab = index > 0 ? index : 0;
  }
}
