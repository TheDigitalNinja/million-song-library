import { ROLE_ANONYMOUS, USER_REDIRECT_TO } from '../../constants.js';

/**
 * Angular config for Library page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function libraryRoute($stateProvider) {
  'ngInject';

  $stateProvider.state(
    'msl.library', {
      url: '/my-library',
      template: require('./library.html'),
      controller: 'libraryCtrl',
      controllerAs: 'vm',
      title: 'My Library',
      data: {
        permissions: {
          except: [ROLE_ANONYMOUS],
          redirectTo: USER_REDIRECT_TO,
        },
      },
    });
}
