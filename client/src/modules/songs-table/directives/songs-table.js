/**
 * Songs table directive
 * @returns {{restrict: string, template: *, scope: {loading: string, content: string}, controller: string,
 *     controllerAs: string}}
 */
export default function songsTable () {
  'ngInject';
  return {
    restrict: 'E',
    template: require('../templates/songsTable.html'),
    scope: {
      loading: '=',
      songs: '=',
    },
  };
}
