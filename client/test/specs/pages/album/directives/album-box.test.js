import albumModule from 'pages/album/album.module';

describe('albumBox directive', () => {
  const album = { id: 2, name: 'an_album' };
  let element;

  beforeEach(() => {
    angular.mock.module(albumModule);

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.album = album;

      const elementHTML = `<album-box album='album'></album-box>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the album on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.album).toEqual(album);
    });
  });
});
