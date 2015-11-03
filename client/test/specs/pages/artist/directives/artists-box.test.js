import artistModule from 'pages/artist/artist.module';

describe('artistsBox Directive', () => {
  const artist = {};
  let element;

  beforeEach(() => {
    angular.mock.module(artistModule);

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.artist = artist;

      const elementHTML = `<artist-box artist='artist'></artist-box>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the artist', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.artist).toEqual(artist);
    });
  });
});
