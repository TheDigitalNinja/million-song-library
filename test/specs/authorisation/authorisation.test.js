/* global describe, it, expect, beforeEach, module, jasmine */
import angular from "angular";
import "angular-mocks";
import authorisationModule from "../../../src/authorisation/module";

describe("authorisation", function () {
  var authorisation;

  beforeEach(angular.mock.module(authorisationModule));
  beforeEach(angular.mock.inject(function (_authorisation_) {
    authorisation = _authorisation_;
  }));

  it("should be authorised", function () {
    authorisation.authorise(true);
    expect(authorisation.isAuthorised()).toBeTruthy();
  });

  it("should be not authorised", function () {
    authorisation.authorise(false);
    expect(authorisation.isAuthorised()).toBeFalsy();
  });

  it("should trigger authorized event", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(true);
    expect(listener.calls.count()).toBe(2);
  });

  it("should trigger authorized and then unbind", function () {
    var listener = jasmine.createSpy("listener");
    authorisation.addChangeListener(listener);
    authorisation.authorise(true);
    authorisation.removeChangeListener(listener);
    authorisation.authorise(false);
    expect(listener.calls.count()).toBe(2);
  });
});
