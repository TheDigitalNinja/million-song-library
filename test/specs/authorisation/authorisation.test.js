/* global describe, it, expect, beforeEach, jasmine */
import angular from "angular";
import authorisationModule from "authorisation/module";

describe("authorisation factory", function () {
  var authorisation;

  beforeEach(angular.mock.module(authorisationModule));
  beforeEach(angular.mock.inject(function (_authorisation_) {
    authorisation = _authorisation_;
  }));

  it("should be authorised", function () {
    authorisation.authorise();
    expect(authorisation.isAuthorised()).toBeTruthy();
  });

  it("should be not authorised by default", function () {
    expect(authorisation.isAuthorised()).toBeFalsy();
  });

  it("should trigger authorized event", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise();
    expect(listener.calls.count()).toBe(2);
  });

  it("should trigger authorized event when destroying session", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise();
    expect(listener.calls.count()).toBe(2);
    authorisation.destroy();
    expect(listener.calls.count()).toBe(3);
  });

  it("should trigger authorized and then unbind", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise();
    authorisation.removeChangeListener(listener);
    authorisation.destroy();
    expect(listener.calls.count()).toBe(2);
  });
});
