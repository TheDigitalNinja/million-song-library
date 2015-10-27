/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import libraryModule from 'pages/library/library.module.js';

describe('addToLibraryDirective', () => {
  const AN_ID = '2';
  const SONG_TYPE = 'song';
  const ARTIST_TYPE = 'artist';
  const ALBUM_TYPE = 'album';

  let $scope, element, libraryModel;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      libraryModel = jasmine.createSpyObj('libraryModel', ['addAlbumToLibrary', 'addSongToLibrary', 'addArtistToLibrary']);

      $provide.value('libraryModel', libraryModel);
    });

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      $scope.object = { id: AN_ID };

      element = (type) => {
        const elementHTML = `<a add-to-library type='${type}' id='object.id'></a>`;
        const compiledDirective = $compile(elementHTML)($scope);
        $scope.$digest();
        return compiledDirective;
      };
    });
  });

  describe('directive scope', () => {
    let isolatedScope;

    beforeEach(() => {
      isolatedScope = element(SONG_TYPE).isolateScope();
    });

    it('should set the type', () => {
      expect(isolatedScope.type).toEqual(SONG_TYPE);
    });

    it('should set the id', () => {
      expect(isolatedScope.id).toEqual(AN_ID);
    });
  });

  describe('click function', () => {
    it('should add the album to the library', () => {
      element(ALBUM_TYPE).triggerHandler('click');
      expect(libraryModel.addAlbumToLibrary).toHaveBeenCalledWith(AN_ID);
    });

    it('should add the artist to the library', () => {
      element(ARTIST_TYPE).triggerHandler('click');
      expect(libraryModel.addArtistToLibrary).toHaveBeenCalledWith(AN_ID);
    });

    it('should add the song to the library', () => {
      element(SONG_TYPE).triggerHandler('click');
      expect(libraryModel.addSongToLibrary).toHaveBeenCalledWith(AN_ID);
    });
  });
});
