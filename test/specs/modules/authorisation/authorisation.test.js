/* global describe, it, expect, beforeEach, jasmine, inject */
import angular from "angular";
import authorisationModule from "modules/authorisation/module";

describe("authorisation factory", function () {
  var $http;
  var loginCredentials = {login: "login", password: "password"};
  var authorisation;
  var storage;
  var sessionToken;

  describe("#1", function () {
    var session = {sessionToken: "sessionToken"};
    var user = {userEmail: "email", userId: "userId"};

    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      storage = jasmine.createSpyObj("storage", ["get", "put", "remove"]);
      sessionToken = jasmine.createSpyObj("sessionToken", ["set", "destroy"]);
      $http = jasmine.createSpyObj("$http", ["post", "get"]);
      $provide.value("storage", storage);
      $provide.value("sessionToken", sessionToken);
      $provide.value("$http", $http);
    }));

    beforeEach(inject(function (_authorisation_, _$http_) {
      authorisation = _authorisation_;
      $http = _$http_;
    }));

    it("should throw authorise error if not credentials passed", done => async function () {
      var reject = jasmine.createSpy("reject");
      await authorisation.authorise({}).then(null, reject);
      expect(reject).toHaveBeenCalled();
      done();
    }());

    it("should be not authorised by default", function () {
      expect(authorisation.isAuthorised()).toBeFalsy();
    });

    it("should authorise, save session and trigger listener", done => async function () {
      var listener = jasmine.createSpy("listener");
      var headers = {headers: {"Content-Type": "application/x-www-form-urlencoded"}};
      $http.post.and.returnValue(Promise.resolve({data: session}));
      $http.get.and.returnValue(Promise.resolve({data: user}));
      authorisation.addChangeListener(listener);
      await authorisation.authorise(loginCredentials);
      expect($http.post.calls.count()).toBe(1);
      expect($http.get.calls.count()).toBe(1);
      expect($http.post.calls.argsFor(0)).toEqual(["/api/loginedge/login", "email=login&password=password", headers]);
      expect($http.get.calls.argsFor(0)).toEqual(["/api/loginedge/sessioninfosessionToken"]);
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(sessionToken.set).toHaveBeenCalledWith("sessionToken");
      expect(storage.get).toHaveBeenCalledWith("authorisation");
      expect(storage.put).toHaveBeenCalledWith("authorisation", user);
      expect(listener.calls.count()).toBe(2);
      done();
    }());

    it("should authorize and destroy session", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      $http.post.and.returnValue(Promise.resolve({data: session}));
      $http.get.and.returnValue(Promise.resolve({data: user}));
      await authorisation.authorise(loginCredentials);
      expect(listener.calls.count()).toBe(2);
      expect($http.post.calls.count()).toBe(1);
      expect($http.get.calls.count()).toBe(1);
      expect(storage.get).toHaveBeenCalled();
      expect(storage.put).toHaveBeenCalled();
      $http.post.and.returnValue(Promise.resolve());
      await authorisation.destroy();
      expect($http.post).toHaveBeenCalledWith("/api/loginedge/logout", {});
      expect(sessionToken.destroy).toHaveBeenCalled();
      expect(listener.calls.count()).toBe(3);
      expect(storage.remove).toHaveBeenCalledWith("authorisation");
      done();
    }());

    it("should get authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      $http.post.and.returnValue(Promise.resolve({data: session}));
      $http.get.and.returnValue(Promise.resolve({data: user}));
      await authorisation.authorise(loginCredentials);
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData("userEmail")).toBe(user.userEmail);
      expect(authorisation.getUserData("userId")).toBe(user.userId);
      expect(authorisation.getUserData()).toEqual(user);
      done();
    }());

    it("should remove authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      $http.post.and.returnValue(Promise.resolve({data: session}));
      $http.get.and.returnValue(Promise.resolve({data: user}));
      await authorisation.authorise(loginCredentials);
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData()).toEqual(user);
      $http.post.and.returnValue(Promise.resolve());
      await authorisation.destroy();
      expect($http.post).toHaveBeenCalledWith("/api/loginedge/logout", {});
      expect(authorisation.getUserData()).toEqual({});
      done();
    }());

    it("should trigger authorized and then unbind", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      $http.post.and.returnValue(Promise.resolve({data: session}));
      $http.get.and.returnValue(Promise.resolve({data: user}));
      await authorisation.authorise(loginCredentials);
      authorisation.removeChangeListener(listener);
      authorisation.destroy();
      expect(listener.calls.count()).toBe(2);
      done();
    }());
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

    it("should have startup data from session", function () {
      expect(storage.get).toHaveBeenCalledWith("authorisation");
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(authorisation.getUserData()).toEqual(loginCredentials);
    });
  });
});
