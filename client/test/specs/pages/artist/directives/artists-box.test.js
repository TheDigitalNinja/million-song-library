import artistModule from 'pages/artist/artist.module';

describe('artistsBox Directive', () => {
  const artist = {};
  let element, $state;

  beforeEach(() => {
    angular.mock.module(artistModule, ($stateProvider) => {
      $stateProvider.state('msl', { url: '/' });
      $stateProvider.state('msl.library', { url: '/library' });
    });

    inject(($compile, $rootScope, _$state_) => {
      const $scope = $rootScope.$new();
      $scope.artist = artist;
      $state = _$state_;

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
