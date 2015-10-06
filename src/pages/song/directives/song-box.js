/**
 * Song box directive
 * @author anram88
 * @returns {{restrict: string, scope: {song: string}, template: *, controller: string, controllerAs: string}}
 */
export default function songBox() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      song: '=',
    },
    template: require('../templates/song-box.html'),
    controller: 'songBoxCtrl',
    controllerAs: 'vm',
  };
}

