/**
 * angular config for default container
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function layoutRoute($stateProvider) {
  'ngInject';

  // default state is used so we do not re-render elements like navigation
  // when user navigates to new default container page
  $stateProvider
    .state('msl', {
      template: require('./layout.html'),
    });
}
