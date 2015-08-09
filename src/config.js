function defaultConfig ($urlRouterProvider, $httpProvider) {
  "ngInject";

  $httpProvider.interceptors.push("sessionTokenHttpInterceptor");

  $urlRouterProvider.when("", "/");
  $urlRouterProvider.otherwise(function ($injector) {
    var $state = $injector.get("$state");
    $state.go("error");
  });
}

export default defaultConfig;
