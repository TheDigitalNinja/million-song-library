/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('libraryCtrl', () => {

  let $scope, $controller, libraryCtrl, $log, libraryModel;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      $log = jasmine.createSpyObj('$log', ['warn', 'error']);
      libraryModel = jasmine.createSpyObj('libraryModel', ['getLibrary']);
      const toastr = jasmine.createSpyObj('toastr', ['error', 'success']);

      $provide.value('$log', $log);
      $provide.value('libraryModel', libraryModel);
      $provide.value('toastr', toastr);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();

      libraryCtrl = $controller('libraryCtrl', {
        $scope: $scope,
        $log: $log,
      });
    });

  });

  afterEach(() => {
    $scope.$destroy();
  });

  it('should create a library controller instance', () => {
    expect(libraryCtrl).toBeDefined();
  });

  describe('constructor', () => {
    it('should get the library again when deleting from library', () => {
      spyOn(libraryCtrl, '_getMyLibrary');
      $scope.$emit('deletedFromLibrary', 'Song');
      expect(libraryCtrl._getMyLibrary).toHaveBeenCalledWith('Song');
    });
  });

  describe('_getMyLibrary', () => {
    it('should get the list of songs', (done) => {
      (async () => {
        await libraryCtrl._getMyLibrary();
        expect(libraryModel.getLibrary).toHaveBeenCalled();
        done();
      })();
    });

    it('should assing the songs to the scope', (done) => {
      (async () => {
        const songs = ['song1'];
        libraryModel.getLibrary.and.returnValue({ songs });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.songs).toBe(songs);
        done();
      })();
    });

    it('should assing the albums to the scope', (done) => {
      (async () => {
        const albums = ['album1'];
        libraryModel.getLibrary.and.returnValue({ albums });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.albums).toBe(albums);
        done();
      })();
    });

    it('should assing the artists to the scope', (done) => {
      (async () => {
        const artists = ['artist1'];
        libraryModel.getLibrary.and.returnValue({ artists });
        await libraryCtrl._getMyLibrary();
        expect(libraryCtrl.artists).toBe(artists);
        done();
      })();
    });

    it('should warn the error', (done) => {
      (async () => {
        const error = new Error('an error');
        libraryModel.getLibrary.and.throwError(error);
        await libraryCtrl._getMyLibrary();
        expect($log.warn).toHaveBeenCalledWith(error);
        done();
      })();
    });
  });
});
