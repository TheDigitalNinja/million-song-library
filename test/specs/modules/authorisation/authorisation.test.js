/* global describe, it, expect, beforeEach, jasmine, inject */
import angular from "angular";
import authorisationModule from "modules/authorisation/module";

describe("authorisation factory", function () {
  var loginCredentials = {login: "login", password: "password"};
  var authorisation;
  var storage;

  describe("#1", function () {
    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      storage = jasmine.createSpyObj("storage", ["get", "put", "remove"]);
      $provide.value("storage", storage);
    }));

    beforeEach(inject(function (_authorisation_) {
      authorisation = _authorisation_;
    }));

    it("should authorise and save session", function () {
      authorisation.authorise(loginCredentials);
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(storage.get).toHaveBeenCalledWith("authorisation");
      expect(storage.put).toHaveBeenCalledWith("authorisation", loginCredentials);
    });

    it("should authorize and destroy session", function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      authorisation.authorise(loginCredentials);
      expect(listener.calls.count()).toBe(2);
      expect(storage.get).toHaveBeenCalledWith("authorisation");
      expect(storage.put).toHaveBeenCalledWith("authorisation", loginCredentials);
      authorisation.destroy();
      expect(listener.calls.count()).toBe(3);
      expect(storage.remove).toHaveBeenCalledWith("authorisation");
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

    it("should trigger authorized and then unbind", function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      authorisation.authorise(loginCredentials);
      authorisation.removeChangeListener(listener);
      authorisation.destroy();
      expect(listener.calls.count()).toBe(2);
    });
  });

  describe("#2", function () {
    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      storage = jasmine.createSpyObj("storage", ["get", "put", "remove"]);
      storage.get.and.returnValue(loginCredentials);
      $provide.value("storage", storage);
    }));

    beforeEach(inject(function (_authorisation_) {
      authorisation = _authorisation_;
    }));

    it("should have data from session", function () {
      expect(storage.get).toHaveBeenCalledWith("authorisation");
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(authorisation.getUserData()).toEqual(loginCredentials);
    });
  });
});
