/**
 * Artist List directive
 * @author anram88
 * @returns {{restrict: string, scope: {artists: string}, template: *, controller: string, controllerAs: string}}
 */
export default function artistsList() {

  'ngInject';

  return {
    restrict: 'E',
    scope: {
      artists: '=',
    },
    template: require('../templates/artist-list.html'),
  };
}

