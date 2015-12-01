export default function defaultRoute ($urlRouterProvider, $httpProvider) {
  'ngInject';

  // makes possible to send httpOnly cookie on query requests and responses
  $httpProvider.defaults.withCredentials = true;
  // when user comes with empty url - this means first page open
  // then redirect it to root page
  $urlRouterProvider.when('', '/');
  // when state is not found then redirect user to error state
  $urlRouterProvider.otherwise(($injector) => {
    const $state = $injector.get('$state');
    $state.go('error');
  });
}
