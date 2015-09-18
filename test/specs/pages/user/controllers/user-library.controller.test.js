/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryPage from 'pages/user/user.module.js';

describe('userLibraryCtrl', function () {
  var $scope, $controller, myLibraryStore;

  beforeEach(
    angular.mock.module(libraryPage, function ($provide) {
        myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch']);
        $provide.value('myLibraryStore', myLibraryStore);
      }
    ));

  beforeEach(inject(function (_$controller_, $rootScope) {
    $controller = _$controller_;
    $scope = $rootScope.$new();
  }));

  afterEach(function () {
    $scope.$destroy();
  });

  it('should fetch library store content on initialization', done => async function () {
    $controller('userLibraryCtrl', {
      $scope: $scope,
      myLibraryStore: myLibraryStore,
    });
    myLibraryStore.fetch.and.returnValue(Promise.resolve());
    expect(myLibraryStore.fetch).toHaveBeenCalled();
    done();
  }());

});
