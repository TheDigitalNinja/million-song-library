/**
 * Angular config for Home page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function homeRoute($stateProvider) {
  'ngInject';

  $stateProvider.state(
    'msl.home', {
      url: '/',
      template: require('./home.html'),
      controller: 'homeCtrl',
      controllerAs: 'vm',
      title: 'Home',
    });
}
