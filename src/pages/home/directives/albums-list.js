export default function albumsList() {

  'ngInject';

  return {
    restrict: 'E',
    template: require('../templates/albums-list.html'),
    controller: 'homeCtrl',
    controllerAs: 'vm',
  };
}

