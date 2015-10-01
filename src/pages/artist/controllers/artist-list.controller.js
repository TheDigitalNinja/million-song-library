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
    //} else {
    //  $state.go('msl.home');
    //}
  }



}

