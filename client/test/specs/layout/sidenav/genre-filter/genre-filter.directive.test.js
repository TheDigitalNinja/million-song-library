/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import genreFilter from 'layout/sidenav/genre-filter/genre-filter.module.js';

describe('genreFilterDirective', () => {
  const listener = {};

  let $scope, element;

  beforeEach(() => {
    angular.mock.module(genreFilter, ($provide) => {
      const facetStore = jasmine.createSpyObj('facetStore', ['fetch']);
      const filterModel = jasmine.createSpyObj('filterModel', ['filter']);

      $provide.value('facetStore', facetStore);
      $provide.value('filterModel', filterModel);
    });

    inject(($compile, $rootScope) => {
      $scope = $rootScope.$new();
      $scope.listener = listener;

      element = () => {
        const elementHTML = `<genre-filter listener='listener'></genre-filter>`;
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
