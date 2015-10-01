export default function albumsList() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      albums: '=',
    },
    template: require('../templates/albums-list.html'),
  };
}

