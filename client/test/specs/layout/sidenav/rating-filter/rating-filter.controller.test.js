/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import ratingFilter from 'layout/sidenav/rating-filter/rating-filter.module.js';

describe('ratingFilterCtrl', () => {
  const RATE = '4';
  const listener = {};

  let $scope, element, facetStore, ratingFilterCtrl, $location, filterModel, $log;

  beforeEach(() => {
    angular.mock.module(ratingFilter, ($provide) => {
      facetStore = jasmine.createSpyObj('facetStore', ['fetch']);
      filterModel = jasmine.createSpyObj('filterModel', ['filter', 'setSelectedRating']);
      $location = jasmine.createSpyObj('$location', ['search']);
      $log = jasmine.createSpyObj('$log', ['error']);

      $provide.value('facetStore', facetStore);
      $provide.value('filterModel', filterModel);
      $provide.value('$location', $location);
      $provide.value('$log', $log);
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

    filterModel.setSelectedRating.and.callFake((rating) => filterModel.selectedRating = rating);
  });

  describe('activeRating', () => {
    it('should return true if the rating is the selectedRating', () => {
      const rate = RATE;
      const controller = ratingFilterCtrl();
      filterModel.selectedRating = RATE;

      expect(controller.isActiveRating(rate)).toBeTruthy();
    });

    it('should return false when the rating is not the ratingFilter', () => {
      const controller = ratingFilterCtrl();
      const rate = RATE;

      expect(controller.isActiveRating(rate)).toBeFalsy();
    });
  });

  describe('getStars', () => {
    it('should return four filled stars', () => {
      const rating = {name: '4 stars and up'};
      const controller = ratingFilterCtrl();
      const response = controller.getStars(rating);
      const filledStar = { filled: true };
      const emptyStar = { filled: false };
      const expected = [filledStar, filledStar, filledStar, filledStar, emptyStar];
      expect(response).toEqual(expected);
    });

    it('should log a warn if an error is thrown', (done) => {
      (async () => {
        const rating = {name: 'sdfgh'};
        const controller = ratingFilterCtrl();
        const response = controller.getStars(rating);

        expect($log.error).toHaveBeenCalledWith('NaN');
        done();
      })();
    });
  });

  describe('applyRatingFilter', () => {
    let controller;

    beforeEach(() => {
      controller = ratingFilterCtrl();
      controller.applyRatingFilter(RATE);
    });

    it('should update the search query', () => {
      expect($location.search).toHaveBeenCalledWith('rating', RATE);
    });

    it('should filter by the rating', () => {
      expect(filterModel.filter).toHaveBeenCalledWith(listener);
    });
  });

  describe('getRatingFacets', () => {
    it('should set the children of the facetList', (done) => {
      (async () => {
        let controller = ratingFilterCtrl();
        const ratingFacets = { children: ['aFacetId'] };
        facetStore.fetch.and.returnValue(ratingFacets);
        await controller._getRatingFacets();
        expect(controller.ratingFacets).toEqual(ratingFacets.children);
        done();
      })();
    });
  });
});
