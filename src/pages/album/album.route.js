/**
 * Angular config for Album page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function albumPageRoute($stateProvider) {

  'ngInject';

  $stateProvider.state(
    'msl.album', {
      url: '/album/:albumId',
      template: require('./templates/album.html'),
      controller: 'albumCtrl',
      controllerAs: 'vm',
    });
}
