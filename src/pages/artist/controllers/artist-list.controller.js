/**
 * Artist list page controller
 * @param {$rootScope.Scope} $scope
 * @param {ui.router.state.$state} $state
 * @param {artistStore} artistStore
 * @param {$logProvider.$log} $log
 */

export default class artistListCtrl {
  /*@ngInject*/

  constructor($scope, $state, artistStore, $log) {
    //if (/* if authenticated*/ true) {
    //  //TODO initiate
    this.$log = $log;
    this.$scope = $scope;
    this.artistStore = artistStore;
    this.getArtists();

    //} else {
    //  $state.go('msl.home');
    //}
  }

  getArtists() {
    (async () => {
      try {
        const artistList = await this.artistStore.fetchAll();
        this.artists = artistList.artists;
        this.$scope.$evalAsync();
      }
      catch (err) {
        this.artists = [];
        this.$log.warn(err);
      }
    })();
  }

}

