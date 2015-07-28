/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from "angular";
import defaultContainer from "containers/default/module";

describe("headerCtrl", function () {
  var $controller;
  var $scope;
  var $state;
  var authorisation;

  beforeEach(angular.mock.module(defaultContainer, function ($provide) {
    $state = jasmine.createSpyObj("$state", ["go"]);
    authorisation = jasmine.createSpyObj("authorisation", [
      "authorise",
      "isAuthorised",
      "addChangeListener",
      "removeChangeListener"
    ]);
    $provide.value("$state", $state);
    $provide.value("authorisation", authorisation);
  }));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  it("should change authorise flag when state changed and change current state", function () {
    var headerCtrl = $controller("headerCtrl", {$scope});
    headerCtrl.logout();
    expect(authorisation.authorise).toHaveBeenCalled();
    expect($state.go).toHaveBeenCalledWith("default.login");
  });

  it("should change authorised flag when state changes", function () {
    var bind, headerCtrl;
    authorisation.addChangeListener.and.callFake(cb => bind = cb);
    headerCtrl = $controller("headerCtrl", {$scope});
    authorisation.isAuthorised.and.returnValue(false);
    bind();
    expect(headerCtrl.authorised).toBeFalsy();
    authorisation.isAuthorised.and.returnValue(true);
    bind();
    expect(headerCtrl.authorised).toBeTruthy();
  });

  it("should not call change listener when state changes after scope is destroyed", function () {
    var bind, headerCtrl;
    authorisation.addChangeListener.and.callFake(cb => bind = cb);
    headerCtrl = $controller("headerCtrl", {$scope});
    authorisation.isAuthorised.and.returnValue(false);
    bind();
    expect(headerCtrl.authorised).toBeFalsy();
    $scope.$destroy();
    expect(authorisation.removeChangeListener).toHaveBeenCalledWith(bind);
  });
});
