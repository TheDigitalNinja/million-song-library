/**
 * Angular config for Artist page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function artistPageRoute($stateProvider) {
  'ngInject';
  $stateProvider.state(
    'msl.artist', {
      url: '/artists/:artistId',
      template: require('./templates/artist.html'),
      controller: 'artistCtrl',
      controllerAs: 'vm',
      title: 'Artist',
    });

    $stateProvider.state(
    'msl.artistsList', {
      url: '/artists',
      template: require('./templates/artist-list.html'),
      controller: 'artistListCtrl',
      controllerAs: 'vm',
      title: 'Artists',
    });
}
