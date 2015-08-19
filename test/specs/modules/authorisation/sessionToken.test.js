/* global describe, beforeEach, inject, it, expect, jasmine */
import angular from "angular";
import authorisationModule from "modules/authorisation/module";

describe("sessionToken", function () {
  var $cookies;
  var sessionToken;

  beforeEach(angular.mock.module(authorisationModule, function ($provide) {
    $cookies = jasmine.createSpyObj("$cookies", ["put", "get", "remove"]);
    $provide.value("$cookies", $cookies);
  }));

  beforeEach(inject(function (_sessionToken_) {
    sessionToken = _sessionToken_;
  }));

  it("should set token to cookie", function () {
    sessionToken.set("token");
    expect($cookies.put).toHaveBeenCalledWith("sessionId", "token");
  });

  it("should get token from cookie", function () {
    $cookies.get.and.returnValue("token");
    expect(sessionToken.get()).toBe("token");
    expect($cookies.get).toHaveBeenCalledWith("sessionId");
  });

  it("should have cookie data set", function () {
    $cookies.get.and.returnValue("token");
    expect(sessionToken.has()).toBeTruthy();
    expect($cookies.get).toHaveBeenCalledWith("sessionId");
  });

  it("should remove cookie data", function () {
    sessionToken.destroy();
    expect($cookies.remove).toHaveBeenCalledWith("sessionId");
  });
});
