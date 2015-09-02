/**
 * Search page controller
 * @param {ui.router.state.$stateParams} $stateParams
 */
export default function searchCtrl($stateParams, $log) {
  'ngInject';

  var vm = this;


  function init() {
    // Get search query from $stateParams
    vm.searchQuery = $stateParams.query;
    if (vm.searchQuery) {
      $log.debug('searched for: ' + vm.searchQuery);
    } else {
      $log.error('Queried for empty string');
    }
  }

  init();
}
