import albumModule from 'pages/album/album.module';

describe('albumList directive', () => {
  const albumsList = ['album'];
  let element;

  beforeEach(() => {
    angular.mock.module(albumModule);

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.albums = albumsList;

      const elementHTML = `<albums-list albums='albums'></albums-list>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the albums on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.albums).toEqual(albumsList);
    });
  });
});
