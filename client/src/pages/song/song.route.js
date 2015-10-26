/**
 * Angular config for Song page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function songPageRoute($stateProvider) {
  'ngInject';
  $stateProvider.state(
    'msl.song', {
      url: '/song/:songId',
      template: require('./song.html'),
      controller: 'songCtrl',
      controllerAs: 'vm',
    });

}
