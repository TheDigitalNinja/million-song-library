/* global describe, it, beforeEach, afterEach, inject */
import angular from "angular";
import defaultMyPage from "containers/default/pages/my/module";

describe("myLibrary", function () {
  var myLibrary;
  var httpBackend;

  beforeEach(angular.mock.module(defaultMyPage, function () {
    //
  }));

  beforeEach(inject(function (_myLibrary_, _$httpBackend_) {
    myLibrary = _myLibrary_;
    httpBackend = _$httpBackend_;
  }));

  afterEach(function () {
    httpBackend.verifyNoOutstandingExpectation();
    httpBackend.verifyNoOutstandingRequest();
  });

  it("should ", done => async function () {
    httpBackend.expectGET("/api/accountedge/users/mylibrary").respond({songs: []});
    var myLibraryFetch = myLibrary.fetch();
    await new Promise(resolve => setTimeout(resolve, 0));
    httpBackend.flush();
    await myLibraryFetch;
    done();
  }());
});
