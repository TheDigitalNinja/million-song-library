/**
 * Song list directive
 * @author anram88
 * @returns {{restrict: string, scope: {songs: string}, template: *, controller: string, controllerAs: string}}
 */
export default function songList() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      songs: '=',
    },
    template: require('../templates/songs-list.html'),
  };
}

