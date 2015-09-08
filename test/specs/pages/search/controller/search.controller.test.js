/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import searchPage from 'pages/search/search.module.js';

describe('searchCtrl', function () {
  var $scope, $stateParams, $controller, $log;

  beforeEach(
    angular.mock.module(searchPage, function ($provide) {
        $stateParams = {query: 'someQuery'};
        $provide.value('$stateParams', $stateParams);
        $log = jasmine.createSpyObj('$log', ['error', 'debug']);
        $provide.value('$log', $log);
      }
    ));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it('should log debug on query param present', function () {
    $stateParams = {query: 'someQuery'};
    $controller('searchCtrl', {
      $scope: $scope,
      $stateParams: $stateParams
    });
    expect($log.debug).toHaveBeenCalledWith('searched for: someQuery');
  });

  it('should log error on query param missing', function () {
    $stateParams = {query: ''};
    $controller('searchCtrl', {
      $scope: $scope,
      $stateParams: $stateParams
    });
    expect($log.error).toHaveBeenCalled();
  });
});
