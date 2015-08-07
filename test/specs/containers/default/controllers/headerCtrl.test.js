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
      "destroy",
      "isAuthorised",
      "addChangeListener",
      "removeChangeListener",
      "getUserData"
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
    expect(authorisation.destroy).toHaveBeenCalled();
    expect($state.go).toHaveBeenCalledWith("default.login");
  });

  it("should change authorised scope data", function () {
    var bind, headerCtrl;
    authorisation.addChangeListener.and.callFake(cb => bind = cb);
    headerCtrl = $controller("headerCtrl", {$scope});
    authorisation.isAuthorised.and.returnValue(false);
    bind();
    expect(headerCtrl.authorised).toBeFalsy();
    expect(authorisation.getUserData).not.toHaveBeenCalled();
    expect();
    authorisation.isAuthorised.and.returnValue(true);
    authorisation.getUserData.and.returnValue("login@email");
    bind();
    expect(headerCtrl.authorised).toBeTruthy();
    expect(authorisation.getUserData).toHaveBeenCalledWith("email");
    expect(headerCtrl.email).toBe("login@email");
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
