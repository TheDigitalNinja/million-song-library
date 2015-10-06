/**
 * Albums list directive
 * @author anram88
 * @returns {{restrict: string, scope: {albums: string}, template: *}}
 */
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

