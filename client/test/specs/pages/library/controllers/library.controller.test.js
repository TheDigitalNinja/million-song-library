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

  describe('getLibrarySongs', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        await libraryCtrl.getLibrarySongs();
        expect(myLibraryStore.fetch).toHaveBeenCalled();
        done();
      })();
    });

    it('should assing the songs to the scope', (done) => {
      (async () => {
        const songs = ['song1'];
        myLibraryStore.fetch.and.returnValue({ songs });
        await libraryCtrl.getLibrarySongs();
        expect(libraryCtrl.songs).toBe(songs);
        done();
      })();
    });

    it('should warn the error', (done) => {
      (async () => {
        const error = new Error('an error');
        myLibraryStore.fetch.and.throwError(error);
        await libraryCtrl.getLibrarySongs();
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });
});
