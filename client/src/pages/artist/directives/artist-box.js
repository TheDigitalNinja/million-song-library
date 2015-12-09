/**
 * Artist box directive
 * @author anram88
 * @returns {{restrict: string, scope: {artist: string}, template: *, controller: string, controllerAs: string}}
 */
export default function artistsBox() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      artist: '=',
    },
    template: require('../templates/artist-box.html'),
    controller: 'artistBoxCtrl',
    controllerAs: 'vm',
  };
}

