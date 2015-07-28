/* global describe, it, expect, beforeEach, jasmine */
import angular from "angular";
import authorisationModule from "authorisation/module";

describe("authorisation factory", function () {
  var authorisation;
  var loginCredentials = {login: "login", password: "password"};

  beforeEach(angular.mock.module(authorisationModule));
  beforeEach(angular.mock.inject(function (_authorisation_) {
    authorisation = _authorisation_;
  }));

  it("should be authorised", function () {
    authorisation.authorise(loginCredentials);
    expect(authorisation.isAuthorised()).toBeTruthy();
  });

  it("should throw authorise error if not credentials passed", function () {
    expect(() => authorisation.authorise({})).toThrow();
  });

  it("should be not authorised by default", function () {
    expect(authorisation.isAuthorised()).toBeFalsy();
  });

  it("should trigger authorized event", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(loginCredentials);
    expect(listener.calls.count()).toBe(2);
  });

  it("should get authorized user data", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(loginCredentials);
    expect(listener.calls.count()).toBe(2);
    expect(authorisation.getUserData("login")).toBe(loginCredentials.login);
    expect(authorisation.getUserData("password")).toBe(loginCredentials.password);
    expect(authorisation.getUserData()).toEqual(loginCredentials);
  });

  it("should remove authorized user data", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(loginCredentials);
    expect(listener.calls.count()).toBe(2);
    expect(authorisation.getUserData()).toEqual(loginCredentials);
    authorisation.destroy();
    expect(authorisation.getUserData()).toEqual({});
  });

  it("should trigger authorized event when destroying session", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(loginCredentials);
    expect(listener.calls.count()).toBe(2);
    authorisation.destroy();
    expect(listener.calls.count()).toBe(3);
  });

  it("should trigger authorized and then unbind", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(loginCredentials);
    authorisation.removeChangeListener(listener);
    authorisation.destroy();
    expect(listener.calls.count()).toBe(2);
  });
});
