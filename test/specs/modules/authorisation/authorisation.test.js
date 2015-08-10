/* global describe, it, expect, beforeEach, afterEach, jasmine, inject */
import angular from "angular";
import authorisationModule from "modules/authorisation/module";

describe("authorisation factory", function () {
  var loginCredentials = {login: "login", password: "password"};
  var authorisation;
  var $cookies;
  var sessionToken;
  var httpBackend;

  describe("#1", function () {
    var session = {sessionToken: "sessionToken"};
    var user = {userEmail: "email", userId: "userId"};

    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      $cookies = jasmine.createSpyObj("$cookies", ["getObject", "putObject", "remove"]);
      sessionToken = jasmine.createSpyObj("sessionToken", ["set", "destroy"]);
      $provide.value("$cookies", $cookies);
      $provide.value("sessionToken", sessionToken);
    }));

    beforeEach(inject(function (_authorisation_, _$http_, _$httpBackend_) {
      authorisation = _authorisation_;
      httpBackend = _$httpBackend_;
    }));

    afterEach(function () {
      httpBackend.verifyNoOutstandingExpectation();
      httpBackend.verifyNoOutstandingRequest();
    });

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
      var authorise = authorisation.authorise(loginCredentials);
      httpBackend.expectPOST("/api/loginedge/login", "email=login&password=password").respond(session);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      httpBackend.expectGET("/api/loginedge/sessioninfo/sessionToken").respond(user);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await authorise;
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(sessionToken.set).toHaveBeenCalledWith("sessionToken");
      expect($cookies.getObject).toHaveBeenCalledWith("authorisation");
      expect($cookies.putObject).toHaveBeenCalledWith("authorisation", user);
      expect(listener.calls.count()).toBe(2);
      done();
    }());

    it("should authorize and destroy session", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      var authorise = authorisation.authorise(loginCredentials);
      httpBackend.expectPOST("/api/loginedge/login", "email=login&password=password").respond(session);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      httpBackend.expectGET("/api/loginedge/sessioninfo/sessionToken").respond(user);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await authorise;
      expect(listener.calls.count()).toBe(2);
      expect($cookies.getObject).toHaveBeenCalled();
      expect($cookies.putObject).toHaveBeenCalled();
      var destroy = authorisation.destroy();
      httpBackend.expectPOST("/api/loginedge/logout", {}).respond({});
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await destroy;
      expect(sessionToken.destroy).toHaveBeenCalled();
      expect(listener.calls.count()).toBe(3);
      expect($cookies.remove).toHaveBeenCalledWith("authorisation");
      done();
    }());

    it("should get authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      var authorise = authorisation.authorise(loginCredentials);
      httpBackend.expectPOST("/api/loginedge/login", "email=login&password=password").respond(session);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      httpBackend.expectGET("/api/loginedge/sessioninfo/sessionToken").respond(user);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await authorise;
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData("userEmail")).toBe(user.userEmail);
      expect(authorisation.getUserData("userId")).toBe(user.userId);
      expect(authorisation.getUserData()).toEqual(user);
      done();
    }());

    it("should remove authorized user data", done => async function () {
      var listener = jasmine.createSpy("listener");
      authorisation.addChangeListener(listener);
      var authorise = authorisation.authorise(loginCredentials);
      httpBackend.expectPOST("/api/loginedge/login", "email=login&password=password").respond(session);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      httpBackend.expectGET("/api/loginedge/sessioninfo/sessionToken").respond(user);
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await authorise;
      expect(listener.calls.count()).toBe(2);
      expect(authorisation.getUserData()).toEqual(user);
      authorisation.removeChangeListener(listener);
      var destroy = authorisation.destroy();
      httpBackend.expectPOST("/api/loginedge/logout", {}).respond({});
      await new Promise(resolve => setTimeout(resolve, 0));
      httpBackend.flush();
      await destroy;
      expect(authorisation.getUserData()).toEqual({});
      expect(listener.calls.count()).toBe(2);
      done();
    }());
  });

  describe("#2", function () {
    beforeEach(angular.mock.module(authorisationModule, function ($provide) {
      $cookies = jasmine.createSpyObj("$cookies", ["getObject", "putObject", "remove"]);
      $cookies.getObject.and.returnValue(loginCredentials);
      $provide.value("$cookies", $cookies);
    }));

    beforeEach(inject(function (_authorisation_) {
      authorisation = _authorisation_;
    }));

    it("should have startup data from session", function () {
      expect($cookies.getObject).toHaveBeenCalledWith("authorisation");
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(authorisation.getUserData()).toEqual(loginCredentials);
    });
  });
});
