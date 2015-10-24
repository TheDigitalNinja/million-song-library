import {ANONYMOUS_REDIRECT_TO, ROLE_ANONYMOUS} from 'constants';

/**
 * angular config for login page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function loginPageRoute($stateProvider) {

  'ngInject';

  $stateProvider.state(
    'msl.login',
    {
      url: '/login',

      template: require('./login.html'),
      controller: 'loginCtrl',
      controllerAs: 'vm',
      data: {
        permissions: {
          only: [ROLE_ANONYMOUS],
          redirectTo: ANONYMOUS_REDIRECT_TO,
        },
      },
      title: 'Login',
    });
}
