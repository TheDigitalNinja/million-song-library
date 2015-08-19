/**
 * Search page controller
 * @param {ui.router.state.$stateParams} $stateParams
 */
function searchCtrl (
  $stateParams
) {
  "ngInject";

  // Get search query from $stateParams
  this.searchQuery = ($stateParams.query) ? $stateParams.query : "";
}

export default searchCtrl;
