/* global describe, it, expect, beforeEach, jasmine, inject */
import angular from "angular";
import authorisationModule from "modules/authorisation/module";

describe("authorisation factory", function () {
  var loginCredentials = {login: "login", password: "password"};
  var user = {userEmail: "email", userId: "userId"};
  var session = {sessionToken: "sessionToken"};

  /** @type {authorisation} */
  var authorisation;
  var $cookies;
  var sessionToken;
  var loginStore;
  var sessionInfoStore;
  var logoutStore;

  describe("#1", function () {
    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      $cookies = jasmine.createSpyObj("$cookies", ["getObject", "putObject", "remove"]);
      sessionToken = jasmine.createSpyObj("sessionToken", ["set", "destroy"]);
      loginStore = jasmine.createSpyObj("loginStore", ["push"]);
      sessionInfoStore = jasmine.createSpyObj("sessionInfoStore", ["fetch"]);
      logoutStore = jasmine.createSpyObj("logoutStore", ["push"]);
      $provide.value("$cookies", $cookies);
      $provide.value("sessionToken", sessionToken);
      $provide.value("loginStore", loginStore);
      $provide.value("sessionInfoStore", sessionInfoStore);
      $provide.value("logoutStore", logoutStore);
    }));

    beforeEach(inject(function (_authorisation_) {
      authorisation = _authorisation_;
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
      authorisation.addChangeListener(listener);
      loginStore.push.and.returnValue(Promise.resolve(session));
      sessionInfoStore.fetch.and.returnValue(Promise.resolve(user));
      await authorisation.authorise(loginCredentials.login, loginCredentials.password);
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(loginStore.push).toHaveBeenCalledWith(loginCredentials.login, loginCredentials.password);
      expect(sessionInfoStore.fetch).toHaveBeenCalledWith(session.sessionToken);
      expect(sessionToken.set).toHaveBeenCalledWith("sessionToken");
      expect($cookies.getObject).toHaveBeenCalledWith("authorisation");
      expect($cookies.putObject).toHaveBeenCalledWith("authorisation", user);
      expect(listener.calls.count()).toBe(2);
      done();
    }());

    it("should authorize and destroy session", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      loginStore.push.and.returnValue(Promise.resolve(session));
      sessionInfoStore.fetch.and.returnValue(Promise.resolve(user));
      await authorisation.authorise(loginCredentials.login, loginCredentials.password);
      expect(listener.calls.count()).toBe(2);
      expect($cookies.getObject).toHaveBeenCalled();
      expect($cookies.putObject).toHaveBeenCalled();
      logoutStore.push.and.returnValue(Promise.resolve());
      await authorisation.destroy();
      expect(logoutStore.push).toHaveBeenCalled();
      expect(sessionToken.destroy).toHaveBeenCalled();
      expect(listener.calls.count()).toBe(3);
      expect($cookies.remove).toHaveBeenCalledWith("authorisation");
      done();
    }());

    it("should get authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      loginStore.push.and.returnValue(Promise.resolve(session));
      sessionInfoStore.fetch.and.returnValue(Promise.resolve(user));
      await authorisation.authorise(loginCredentials.login, loginCredentials.password);
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData("userEmail")).toBe(user.userEmail);
      expect(authorisation.getUserData("userId")).toBe(user.userId);
      expect(authorisation.getUserData()).toEqual(user);
      done();
    }());

    it("should remove authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      loginStore.push.and.returnValue(Promise.resolve(session));
      sessionInfoStore.fetch.and.returnValue(Promise.resolve(user));
      await authorisation.authorise(loginCredentials.login, loginCredentials.password);
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData()).toEqual(user);
      authorisation.removeChangeListener(listener);
      logoutStore.push.and.returnValue(Promise.resolve());
      await authorisation.destroy();
      expect(authorisation.getUserData()).toEqual({});
      expect(listener.calls.count()).toBe(2);
      done();
    }());
  });

  describe("#2", function () {
    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      $cookies = jasmine.createSpyObj("$cookies", ["getObject", "putObject", "remove"]);
      sessionToken = jasmine.createSpyObj("sessionToken", ["set", "destroy"]);
      loginStore = jasmine.createSpyObj("loginStore", ["push"]);
      sessionInfoStore = jasmine.createSpyObj("sessionInfoStore", ["fetch"]);
      logoutStore = jasmine.createSpyObj("logoutStore", ["push"]);
      $provide.value("$cookies", $cookies);
      $provide.value("sessionToken", sessionToken);
      $provide.value("loginStore", loginStore);
      $provide.value("sessionInfoStore", sessionInfoStore);
      $provide.value("logoutStore", logoutStore);
      $cookies.getObject.and.returnValue(user);
    }));

    beforeEach(inject(function (_authorisation_) {
      authorisation = _authorisation_;
    }));

    it("should have startup data from session", function () {
      expect($cookies.getObject).toHaveBeenCalledWith("authorisation");
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(authorisation.getUserData()).toEqual(user);
    });
  });
});
