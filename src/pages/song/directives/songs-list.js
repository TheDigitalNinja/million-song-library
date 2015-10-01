export default function songList() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      songs: '=',
    },
    template: require('../templates/songs-list.html'),
    controller: 'songsListCtrl',
    controllerAs: 'vm',
  };
}

