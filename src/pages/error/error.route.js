/**
 * angular config for error module
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function errorPageRoute($stateProvider) {

  'ngInject';

  $stateProvider.state(
    'error', {
      template: require('./error.html'),
      title: 'Error',
    });
}
