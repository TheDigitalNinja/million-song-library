import _ from 'lodash';

class libraryCarouselCtrl {
  /*@ngInject*/

  constructor($scope) {
    this.entity = $scope.entity;
    this.slides = $scope.slides;

    this.slider = `slider-`;
    this.title = `${ _.capitalize(this.entity) }s`;
    this.sliderId = `${this.slider}${this.entity}`;
    this.triggerSlick = false;


    // Callback handler for the end of ng-repeat.
    this.repeatComplete = function() {
      $scope.triggerSlick = true;
    };

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
    controller: libraryCarouselCtrl,
    controllerAs: 'vm',
    scope: {
      entity: '@',
      sliderId: '@',
      slides: '=',
      triggerSlick: '@',
    },
    link: link,
  };

  function link(scope, element, attrs, ctrl) {
    let slider;

    function slickIt() {
      // Slick requires jQuery objects
      (($) => {
        slider = $(element).find(`#${ ctrl.sliderId }`);
        $(slider).slick({
          slidesToShow: 2,
          slidesToScroll: 1,
          mobileFirst: true,
          responsive: [
            {
              breakpoint: 768,
              settings: {
                slidesToShow: 3,
              },
            },
            {
              breakpoint: 1024,
              settings: {
                slidesToShow: 4,
              },
            },
          ],
        });
      })(jQuery);
    }

    function destroy() {
      // Slick requires jQuery objects
      (($) => {
        if(slider) {
          $(slider).slick('unslick');
        }
      })(jQuery);
    }

    scope.$watch('triggerSlick',
      (newValue, oldValue) => {
        if(newValue === true) {
          slickIt();
        }
      });

    scope.$on('$destroy', destroy);
  }
}
