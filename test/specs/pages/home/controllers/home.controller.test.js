/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import homePage from 'pages/home/home.module.js';

describe('homeCtrl', function () {

  var $scope, $controller, homeCtrl;

  beforeEach(
    angular.mock.module(homePage, function () {}
    ));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
    homeCtrl = $controller('homeCtrl', {$scope: $scope});
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it('should not have empty categories', function () {
    expect(homeCtrl.categories.length).toBeGreaterThan(0);
    expect(homeCtrl.categories.length).toBe(1);
  });

  it('getNumber should return array of length n', function () {
    expect(homeCtrl.getNumber(5).length).toBe(5);
    expect(homeCtrl.getNumber(15).length).toBe(15);
  });

});
