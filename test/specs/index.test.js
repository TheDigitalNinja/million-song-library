/* global describe, it, expect, beforeEach, module */
import ngModule from "../../src/index";
import angular from "angular";
import "angular-mocks";

describe("msl module", function () {
  beforeEach(angular.mock.module(ngModule));
  it("should run without any errors", function () {
    expect(ngModule).toBe("msl");
  });
});
