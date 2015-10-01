export default function artistsList() {

  'ngInject';

  return {
    restrict: 'E',
    scope: true,
    template: require('../../artist/templates/artist-list.html'),
    controller: 'artistListCtrl',
    controllerAs: 'vm',
  };
}

