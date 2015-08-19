function defaultConfig ($urlRouterProvider, $httpProvider) {
  "ngInject";

  // this interceptor provides session id header when user is authorised
  $httpProvider.interceptors.push("sessionTokenHttpInterceptor");

  // when user comes with empty url - this means first page open
  // then redirect it to root page
  $urlRouterProvider.when("", "/");
  // when state is not found then redirect user to error state
  $urlRouterProvider.otherwise(function ($injector) {
    var $state = $injector.get("$state");
    $state.go("error");
  });
}

export default defaultConfig;
