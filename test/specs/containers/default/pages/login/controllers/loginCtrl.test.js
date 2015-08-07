/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from "angular";
import defaultLoginPage from "containers/default/pages/login/module";

describe("loginCtrl", function () {
  var $scope;
  var $state;
  var $controller;
  var authorisation;

  beforeEach(angular.mock.module(defaultLoginPage, function ($provide) {
    $state = jasmine.createSpyObj("$state", ["go"]);
    authorisation = jasmine.createSpyObj("authorisation", ["authorise"]);
    $provide.value("$state", $state);
    $provide.value("authorisation", authorisation);
  }));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  it("should authorise with credentials", done => async function () {
    var loginCtrl = $controller("loginCtrl", {$scope});
    authorisation.authorise.and.returnValue(Promise.resolve(true));
    loginCtrl.email = "test@test.com";
    loginCtrl.password = "password";
    await loginCtrl.submit();
    expect(authorisation.authorise).toHaveBeenCalledWith({login: loginCtrl.email, password: loginCtrl.password});
    expect($state.go).toHaveBeenCalledWith("default.home");
    done();
  }());
});
