import $ from 'jquery';
import libraryModule from 'pages/library/library.module';

describe('library-carousel directive', () => {
  const songs = ['song1', 'song2'];
  let element, $scope;

  beforeEach(() => {
    angular.mock.module(libraryModule);

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      $scope.songs = songs;

      element = () => {
        const elementHTML = `<library-carousel entity='song' slides='songs'> </library-carousel>`;
        const compiledElement = $compile(elementHTML)($scope);
        $scope.$digest();
        return compiledElement;
      };
    });
  });

  describe('scope', () => {
    let compiledElement, isolatedScope;

    beforeEach(() => {
      compiledElement = element();
      isolatedScope = compiledElement.isolateScope();
    });

    it('should get the entity', () => {
      expect(isolatedScope.entity).toBe('song');
    });

    it('should get the songs', () => {
      expect(isolatedScope.slides).toBe(songs);
    });
  });

  describe('link', () => {
    const carousel = '.carousel';
    let compiledElement, isolatedScope;

    beforeEach(() => {
      compiledElement = element();
      isolatedScope = compiledElement.isolateScope();
    });

    it('should initialize the slick slider', () => {
      const slider = $(compiledElement).find(carousel);
      expect(slider.hasClass('slick-initialized')).toBeTruthy();
    });

    it('should unslick the slider when the scope is destroyed', () => {
      $scope.$destroy();
      const slider = $(compiledElement).find(carousel);
      expect(slider.hasClass('slick-initialized')).toBeFalsy();
    });
  });
});
