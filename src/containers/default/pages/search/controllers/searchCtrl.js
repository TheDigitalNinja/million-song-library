function searchCtrl ($stateParams) {
  "ngInject";

  this.searchQuery = ($stateParams.query) ? $stateParams.query : "";
}

export default searchCtrl;
