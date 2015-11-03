import songModule from 'pages/song/song.module';

describe('songBox directive', () => {
  const song = { id: 1, name: 'song name' };
  let element;

  beforeEach(() => {
    angular.mock.module(songModule, ($provide) => {
      const timeFilter = jasmine.createSpy('timeFilter');

      $provide.value('timeFilterFilter', timeFilter);
    });

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.song = song;

      const elementHTML = `<song-box song='song'></song-box>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the song on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.song).toEqual(song);
    });
  });
});

