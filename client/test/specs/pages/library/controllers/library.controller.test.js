/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryCtrl', () => {

  let $scope, $controller, libraryCtrl,
      myLibraryStore, $log;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['warn']);
      myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch']);

      $provide.value('$log', $log);
      $provide.value('myLibraryStore', myLibraryStore);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();

      libraryCtrl = $controller('libraryCtrl', {
        $scope: $scope,
        $log: $log,
        myLibraryStore: myLibraryStore,
      });
    });

  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should create a library controller instance', () => {
    expect(libraryCtrl).toBeDefined();
  });

  it('should get the list of songs', (done) => {
    (async () => {
      await libraryCtrl.getLibrarySongs();
      expect(myLibraryStore.fetch).toHaveBeenCalled();
      done();
    })();
  });

});
