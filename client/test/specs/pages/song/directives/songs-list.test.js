import songModule from 'pages/song/song.module';

describe('songsList directive', () => {
  const songsList = ['song'];
  let element;

  beforeEach(() => {
    angular.mock.module(songModule, ($provide) => {
      const timeFilter = jasmine.createSpy('timeFilter');

      $provide.value('timeFilterFilter', timeFilter);
    });

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.songs = songsList;

      const elementHTML = `<songs-list songs='songs'></songs-list>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the songs on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.songs).toEqual(songsList);
    });
  });
});

