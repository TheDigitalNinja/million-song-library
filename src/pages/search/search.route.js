/**
 * Angular config for Search page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function searchPageRoute($stateProvider) {
  'ngInject';
  $stateProvider.state(
    'msl.route', {
      url: '/search/:query',
      template: require('./search.html'),
      controller: 'searchCtrl',
      controllerAs: 'vm'
    });
}
