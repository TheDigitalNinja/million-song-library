function defaultConfig ($urlRouterProvider) {
  "ngInject";
  $urlRouterProvider.when("", "/");
  $urlRouterProvider.otherwise(function ($injector) {
    var $state = $injector.get("$state");
    $state.go("error");
  });
}

export default defaultConfig;
