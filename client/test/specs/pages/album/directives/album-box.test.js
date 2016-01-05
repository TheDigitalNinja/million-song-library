import albumModule from 'pages/album/album.module';

describe('albumBox directive', () => {
  const album = { id: 2, name: 'an_album' };
  let element, $state;

  beforeEach(() => {
    angular.mock.module(albumModule, ($stateProvider) => {
      $stateProvider.state('msl', { url: '/' });
      $stateProvider.state('msl.library', { url: '/library' });
    });

    inject(($compile, $rootScope, _$state_) => {
      const $scope = $rootScope.$new();
      $scope.album = album;
      $state = _$state_;

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

  describe('controller', () => {
    describe('isLibraryPage', () => {
      it('should check if it is on the library state', () => {
        $state.transitionTo('msl.library');
        const isolatedScope = element.isolateScope();
        isolatedScope.$apply();
        const response = isolatedScope.vm.isLibraryPage();
        expect(response).toBeTruthy();
      });

      it('should be false if the state is different than library', () => {
        $state.transitionTo('msl');
        const isolatedScope = element.isolateScope();
        isolatedScope.$apply();
        const response = isolatedScope.vm.isLibraryPage();
        expect(response).toBeFalsy();
      });
    });
  });
});
