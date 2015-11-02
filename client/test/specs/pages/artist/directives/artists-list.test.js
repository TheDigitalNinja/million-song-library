import artistModule from 'pages/artist/artist.module';

describe('artistsList Directive', () => {
  const artistsList = ['artists'];
  let element;

  beforeEach(() => {
    angular.mock.module(artistModule);

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.artists = artistsList;

      const elementHTML = `<artists-list artists='artists'></artists-list>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the artists', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.artists).toEqual(artistsList);
    });
  });
});
