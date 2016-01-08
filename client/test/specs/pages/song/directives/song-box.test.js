import songModule from 'pages/song/song.module';

describe('songBox directive', () => {
  const song = { id: 1, name: 'song name' };
  let element, $state;

  beforeEach(() => {
    angular.mock.module(songModule, ($provide, $stateProvider) => {
      const timeFilter = jasmine.createSpy('timeFilter');

      $provide.value('timeFilterFilter', timeFilter);
      $stateProvider.state('msl', { url: '/' });
      $stateProvider.state('msl.library', { url: '/library' });
    });

    inject(($compile, $rootScope, _$state_) => {
      const $scope = $rootScope.$new();
      $scope.song = song;
      $state = _$state_;

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

