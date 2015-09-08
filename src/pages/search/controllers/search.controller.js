/**
 * Search page controller
 * @param {ui.router.state.$stateParams} $stateParams
 */
export default class searchCtrl {
  /*@ngInject*/

  constructor($stateParams, $log) {
    var vm = this;
    // Get search query from $stateParams
    vm.searchQuery = $stateParams.query;
    if (vm.searchQuery) {
      $log.debug('searched for: ' + vm.searchQuery);
    } else {
      $log.error('Queried for empty string');
    }
  }
}
