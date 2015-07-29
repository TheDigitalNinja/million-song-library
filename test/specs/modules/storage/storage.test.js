/* global describe, beforeEach, inject, jasmine, it, expect */
import angular from "angular";
import storageModule from "modules/storage/module";

describe("storage factory", function () {
  var $cookies;
  var storage;

  beforeEach(angular.mock.module(storageModule, function ($provide) {
    $cookies = jasmine.createSpyObj("$cookies", ["get", "put", "remove"]);
    $provide.value("$cookies", $cookies);
  }));

  beforeEach(inject(function (_storage_) {
    storage = _storage_;
  }));

  it("should get json data from $cookies", function () {
    $cookies.get.and.returnValue("{\"a\":1}");
    var data = storage.get("test");
    expect(data).toEqual({a: 1});
    expect($cookies.get).toHaveBeenCalledWith("test");
    expect($cookies.get.calls.count()).toBe(1);
  });

  it("should put json data to $cookies", function () {
    storage.put("test", {a: 1});
    expect($cookies.put).toHaveBeenCalledWith("test", "{\"a\":1}");
    expect($cookies.put.calls.count()).toBe(1);
  });

  it("should remove data from $cookies", function () {
    storage.remove("test");
    expect($cookies.remove).toHaveBeenCalledWith("test");
    expect($cookies.remove.calls.count()).toBe(1);
  });
});
