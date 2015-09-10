import {USER_REDIRECT_TO, ROLE_USER} from 'constants';

/**
 * angular config for my page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
export default function userRoute($stateProvider) {

  'ngInject';

  $stateProvider
    .state('msl.user', {
      template: require('./templates/user.html'),
      data: {
        permissions: {
          only: [ROLE_USER],
          redirectTo: USER_REDIRECT_TO,
        },
      },
    })
    .state('msl.user.history', {
      url: '/my/history',
      template: require('./templates/user-history.html'),
      controller: 'userHistoryCtrl',
      controllerAs: 'vm',
    })
    .state('msl.user.likes', {
      url: '/my/likes',
      template: require('./templates/user-likes.html'),
      controller: 'userLikesCtrl',
      controllerAs: 'vm',
    })
    .state('msl.user.library', {
      url: '/my/library',
      template: require('./templates/user-library.html'),
      controller: 'userLibraryCtrl',
      controllerAs: 'vm',
    });
}
