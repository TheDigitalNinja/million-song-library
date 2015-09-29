export default function browseRoutes($stateProvider) {
  'ngInject';

  $stateProvider.state(
      'msl.browse', {
        url: '/browse?genre&artist&rating',
        template: require('./browse.html'),
        controller: 'browseCtrl',
        controllerAs: 'vm',
        title: 'Browse',
      });
}
