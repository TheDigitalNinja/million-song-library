import songsTableModule from 'modules/songs-table/module';

describe('songsTable directive', () => {
  const isLoading = true;
  const songsList = ['song'];
  let element;

  beforeEach(() => {
    angular.mock.module(songsTableModule, ($provide) => {
      const timeFilter = jasmine.createSpy('timeFilter');

      $provide.value('timeFilterFilter', timeFilter);
    });

    inject(($compile, $rootScope) => {
      const $scope = $rootScope.$new();
      $scope.loading = isLoading;
      $scope.songs = songsList;

      const elementHTML = `<songs-table loading='loading', songs='songs'></songs-table>`;
      element = $compile(elementHTML)($scope);
      $scope.$digest();
    });
  });

  describe('scope', () => {
    it('should set the loading variable on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.loading).toBe(isLoading);
    });

    it('should set the songs on the scope', () => {
      const isolatedScope = element.isolateScope();
      expect(isolatedScope.songs).toBe(songsList);
    });
  });
});
