/**
 * Home page controller
 */
export default class homeCtrl {
  /*@ngInject*/

  /**
   * @constructor
   * @this {vm}
   * @param {albumModel} albumModel
   * @param {artistModel} artistModel
   * @param {genreFilterModel} genreFilterModel
   * @param {$log} $log
   * @param {$rootScope.Scope} $scope
   * @param {songModel} songModel
   */
  constructor(albumModel,
              artistModel,
              genreFilterModel,
              $log,
              $scope,
              songModel) {
    this.$scope = $scope;
    this.$log = $log;
    this.artistModel = artistModel;
    this.albumModel = albumModel;
    this.model = genreFilterModel;
    this.songModel = songModel;
    this.activeTab = 'songs';

    //Initializes data
    songModel.getSongs();
    artistModel.getArtists();
    albumModel.getAlbums();

    this.$scope.$watch(() => genreFilterModel.songs,
      () => {
        if (genreFilterModel.songs !== null) {
          this.songs = genreFilterModel.songs;
        }
      });

    this.$scope.$watch(() => genreFilterModel.artists,
      () => {
        if (genreFilterModel.artists !== null) {
          this.artists = genreFilterModel.artists;
        }
      });

    this.$scope.$watch(() => genreFilterModel.albums,
      () => {
        if (genreFilterModel.albums !== null) {
          this.albumsList = genreFilterModel.albums;
        }
      });
  }
}

