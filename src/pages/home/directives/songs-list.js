export default function songList() {

  'ngInject';

  return {
    restrict: 'E',
    template: require('../templates/songs-list.html'),
    controller: 'homeCtrl',
    controllerAs: 'vm',
  };
}

