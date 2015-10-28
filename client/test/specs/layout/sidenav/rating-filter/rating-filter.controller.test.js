/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import ratingFilter from 'layout/sidenav/rating-filter/rating-filter.module.js';

describe('ratingFilterCtrl', () => {
  const RATE = 4;
  const listener = {};

  let $scope, element, ratingFilterCtrl, $location, filterModel;

  beforeEach(() => {
    angular.mock.module(ratingFilter, ($provide) => {
      filterModel = jasmine.createSpyObj('filterModel', ['filter']);
      $location = jasmine.createSpyObj('$location', ['search']);

      $provide.value('filterModel', filterModel);
      $provide.value('$location', $location);
    });

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      $scope.listener = listener;

      element = () => {
        const elementHTML = `<rating-filter listener='listener'></rating-filter>`;
        const compiledDirective = $compile(elementHTML)($scope);
        $scope.$digest();
        return compiledDirective;
      };

      ratingFilterCtrl = () => {
        return element().controller('ratingFilter');
      };
    });

  });

  describe('activeRating', () => {
    it('should return true if the rating is the ratingFilter', () => {
      const rate = { rate: 4 };
      const controller = ratingFilterCtrl();
      controller.ratingFilter = 4;

      expect(controller.activeRating(rate)).toBeTruthy();
    });

    it('should return false when the rating is not the ratingFilter', () => {
      const controller = ratingFilterCtrl();
      const rate = { rate: 3 };

      expect(controller.activeRating(rate)).toBeFalsy();
    });
  });

  describe('applyRatingFilter', () => {
    let controller, rateObj;

    describe('when object is null', () => {
      beforeEach(() => {
        controller = ratingFilterCtrl();
        controller.applyRatingFilter(null);
      });

      it('should set the controller rating filter', () => {
        expect(controller.ratingFilter).toEqual(null);
      });
    });

    describe('when object exist', () => {

      beforeEach(() => {
        controller = ratingFilterCtrl();
        rateObj = { rate: RATE };
        controller.applyRatingFilter(rateObj);
      });

      it('should set the controller rating filter', () => {
        expect(controller.ratingFilter).toEqual(RATE);
      });

      it('should update the search query', () => {
        expect($location.search).toHaveBeenCalledWith('rating', RATE);
      });

      it('should filter by the given rating', () => {
        expect(filterModel.filter).toHaveBeenCalledWith({ rating: RATE}, listener);
      });
    });
  });
});
