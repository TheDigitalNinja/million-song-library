import libraryModule from 'pages/library/library.module.js';

describe('removeFromLibraryDirective', () => {
  const AN_ID = '2';
  const SONG_TYPE = 'song';
  const ARTIST_TYPE = 'artist';
  const ALBUM_TYPE = 'album';
  const A_TIMESTAMP = 'Thu Feb 05 00:31:49 CST 2015';

  let $scope, element, libraryModel;

  beforeEach(() => {
    angular.mock.module(libraryModule, ($provide) => {
      libraryModel = jasmine.createSpyObj('libraryModel', ['removeAlbumFromLibrary', 'removeSongFromLibrary', 'removeArtistFromLibrary']);

      $provide.value('libraryModel', libraryModel);
    });

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      $scope.object = { id: AN_ID, timestamp: A_TIMESTAMP };

      element = (type) => {
        const elementHTML = `<a remove-from-library type='${type}' id='object.id' timestamp='object.timestamp'></a>`;
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
    it('should remove the album from the library', () => {
      element(ALBUM_TYPE).triggerHandler('click');
      expect(libraryModel.removeAlbumFromLibrary).toHaveBeenCalledWith(AN_ID, A_TIMESTAMP);
    });

    it('should remove the artist from the library', () => {
      element(ARTIST_TYPE).triggerHandler('click');
      expect(libraryModel.removeArtistFromLibrary).toHaveBeenCalledWith(AN_ID, A_TIMESTAMP);
    });

    it('should remove the song from the library', () => {
      element(SONG_TYPE).triggerHandler('click');
      expect(libraryModel.removeSongFromLibrary).toHaveBeenCalledWith(AN_ID, A_TIMESTAMP);
    });
  });
});
