/**
 * Search page controller
 * @param {ui.router.state.$stateParams} $stateParams
 */
export default class searchCtrl {
  /*@ngInject*/

  constructor($stateParams, $log) {
    // Get search query from $stateParams
    this.searchQuery = $stateParams.query;
    if(this.searchQuery) {
      $log.debug(`searched for: ${ this.searchQuery }`);
    }
    else {
      $log.error('Queried for empty string');
    }
  }
}
