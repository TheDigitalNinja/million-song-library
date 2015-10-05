class libraryCarouselCtrl {
  /*@ngInject*/

  constructor($scope) {
    this.title = $scope.title;
    this.slides = $scope.slides;

    $scope.$watch('slides', (slides) => {
      this.slides = slides;
    });
  }
}

export default function LibraryCarousel() {
  'ngInject';

  return {
    restrict: 'E',
    template: require('./library-carousel.html'),
    scope: {
      title: '@',
      slides: '=',
    },
    controller: libraryCarouselCtrl,
    controllerAs: 'vm',
  };
}
