/**
 * Angular config for Artist page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function artistPageRoute($stateProvider) {
  'ngInject';
  $stateProvider.state(
    'msl.artist', {
      url: '/artist/:artistId',
      template: require('./artist.html'),
      controller: 'artistCtrl',
      controllerAs: 'vm',
      title: 'Artist',
    });
}
