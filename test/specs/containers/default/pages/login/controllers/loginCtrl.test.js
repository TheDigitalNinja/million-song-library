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

  it("should trigger authorisation when login with google", function () {
    var loginCtrl = $controller("loginCtrl", {$scope});
    loginCtrl.loginWithGoogle();
    expect(authorisation.authorise).toHaveBeenCalled();
    expect($state.go).toHaveBeenCalledWith("default.home");
  });

  it("should trigger authorisation when login with facebook", function () {
    var loginCtrl = $controller("loginCtrl", {$scope});
    loginCtrl.loginWithFacebook();
    expect(authorisation.authorise).toHaveBeenCalled();
    expect($state.go).toHaveBeenCalledWith("default.home");
  });
});
