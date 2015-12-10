/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */
import { GENRE_FACET_ID } from '../../../../../src/constants.js';

import genreFilter from 'layout/sidenav/genre-filter/genre-filter.module.js';

describe('genreFilterCtrl', () => {
  const listener = {};
  const genres = ['genre1', 'genre2'];

  let $scope, element, genreFilterCtrl, facetStore, $location, filterModel;

  beforeEach(() => {
    angular.mock.module(genreFilter, ($provide) => {
      facetStore = jasmine.createSpyObj('facetStore', ['fetch']);
      $location = jasmine.createSpyObj('$location', ['search']);
      filterModel = jasmine.createSpyObj('filterModel', ['filter', 'setSelectedGenre']);

      $provide.value('facetStore', facetStore);
      $provide.value('filterModel', filterModel);
      $provide.value('$location', $location);
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

      genreFilterCtrl = () => {
        return element().controller('genreFilter');
      };
    });

    filterModel.setSelectedGenre.and.callFake((genre) => filterModel.selectedGenreId = genre);
  });

  describe('getGenres', () => {
    it('should fetch the genres for the activeGenre', () => {
      const controller = genreFilterCtrl();
      controller.$scope.activeGenre = genres[0];
      facetStore.fetch.and.returnValue({ facets: genres });

      controller._getGenreFacets();
      expect(facetStore.fetch).toHaveBeenCalledWith(GENRE_FACET_ID);
    });
  });

  describe('activeGenre', () => {
    it('should return true if the genre is the selectedGenre', () => {
      const controller = genreFilterCtrl();
      filterModel.selectedGenreId = genres[0];
      const genre = genres[0];

      expect(controller.activeGenre(genre)).toBeTruthy();
    });

    it('should return false when the genre is not the selectedGenre', () => {
      const controller = genreFilterCtrl();
      const genre = { name: genres[0] };

      expect(controller.activeGenre(genre)).toBeFalsy();
    });
  });

  describe('applyFilterByGenre', () => {
    let controller, genre;

    beforeEach(() => {
      controller = genreFilterCtrl();
      genre = { name: genres[0] };

      controller.applyFilterByGenre(genre);
    });

    it('should update the search query', () => {
      expect($location.search).toHaveBeenCalledWith('genre', genre);
    });

    it('should filter by the genre', () => {
      expect(filterModel.filter).toHaveBeenCalledWith(listener);
    });
  });

});
