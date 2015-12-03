/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryCtrl', () => {

  let $scope, $controller, libraryCtrl, myLibraryStore, $log, loginModal;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['warn']);
      myLibraryStore = jasmine.createSpyObj('myLibraryStore', ['fetch']);
      loginModal = jasmine.createSpyObj('loginModal', ['show']);

      $provide.value('$log', $log);
      $provide.value('myLibraryStore', myLibraryStore);
      $provide.value('loginModal', loginModal);
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

  describe('_getMyLibrary', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        await libraryCtrl._getMyLibrary();
        expect(myLibraryStore.fetch).toHaveBeenCalled();
        done();
      })();
    });

    it('should assing the songs to the scope', (done) => {
      (async () => {
        const songs = ['song1'];
        myLibraryStore.fetch.and.returnValue({ songs });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.songs).toBe(songs);
        done();
      })();
    });

    it('should assing the albums to the scope', (done) => {
      (async () => {
        const albums = ['album1'];
        myLibraryStore.fetch.and.returnValue({ albums });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.albums).toBe(albums);
        done();
      })();
    });

    it('should assing the artists to the scope', (done) => {
      (async () => {
        const artists = ['artist1'];
        myLibraryStore.fetch.and.returnValue({ artists });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.artists).toBe(artists);
        done();
      })();
    });

    it('should warn the error', (done) => {
      (async () => {
        const error = new Error('an error');
        myLibraryStore.fetch.and.throwError(error);
        await libraryCtrl._getMyLibrary();
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });
});
