/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import searchPage from 'pages/search/search.module.js';

describe('searchCtrl', () => {
  let $scope, $stateParams, $controller, $log;

  beforeEach(() => {
    angular.mock.module(searchPage, ($provide) => {
      $stateParams = { query: 'someQuery' };
      $provide.value('$stateParams', $stateParams);
      $log = jasmine.createSpyObj('$log', ['error', 'debug']);
      $provide.value('$log', $log);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should log debug on query param present', () => {
    $stateParams = { query: 'someQuery' };
    $controller('searchCtrl', {
      $scope: $scope,
      $stateParams: $stateParams,
    });
    expect($log.debug).toHaveBeenCalledWith('searched for: someQuery');
  });

  it('should log error on query param missing', () => {
    $stateParams = { query: '' };
    $controller('searchCtrl', {
      $scope: $scope,
      $stateParams: $stateParams,
    });
    expect($log.error).toHaveBeenCalled();
  });
});
