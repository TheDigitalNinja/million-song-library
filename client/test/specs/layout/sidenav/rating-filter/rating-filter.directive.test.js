/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import ratingFilter from 'layout/sidenav/rating-filter/rating-filter.module.js';

describe('ratingFilterDirective', () => {
  const listener = {};

  let $scope, element;

  beforeEach(() => {
    angular.mock.module(ratingFilter, ($provide) => {
      const facetStore = jasmine.createSpyObj('facetStore', ['fetch']);
      const filterModel = jasmine.createSpyObj('filterModel', ['filter']);

      $provide.value('facetStore', facetStore);
      $provide.value('filterModel', filterModel);
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
    });

  });

  describe('directive scope', () => {
    it('should set the listener', () => {
      const isolatedScope = element().isolateScope();
      expect(isolatedScope.listener).toEqual(listener);
    });
  });
});
