/**
 * Album box directive
 * @author anram88
 * @returns {{restrict: string, scope: {album: string}, template: *, controller: string, controllerAs: string}}
 */
export default function albumBox() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      album: '=',
    },
    template: require('../templates/album-box.html'),
    controller: 'albumBoxCtrl',
    controllerAs: 'vm',
  };
}

