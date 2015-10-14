/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import genreFilter from 'layout/sidenav/genre-filter/genre-filter.module.js';

describe('genreFilterCtrl', () => {
  const listener = {};
  const genres = ['genre1', 'genre2'];

  let $scope, element, genreFilterCtrl, genreStore, $location, filterModel;

  beforeEach(() => {
    angular.mock.module(genreFilter, ($provide) => {
      genreStore = jasmine.createSpyObj('genreStore', ['fetch']);
      $location = jasmine.createSpyObj('$location', ['search']);
      filterModel = jasmine.createSpyObj('filterModel', ['filter']);

      $provide.value('genreStore', genreStore);
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

  });

  describe('getGenres', () => {
    it('should fetch the genres for the activeGenre', () => {
      const controller = genreFilterCtrl();
      controller.$scope.activeGenre = genres[0];
      genreStore.fetch.and.returnValue({ genres: genres });

      controller.getGenres();
      expect(genreStore.fetch).toHaveBeenCalledWith(genres[0]);
    });
  });

  describe('activeGenre', () => {
    it('should return true if the genre is the selectedGenre', () => {
      const controller = genreFilterCtrl();
      controller.selectedGenre = genres[0];
      const genre = { genreName: genres[0] };

      expect(controller.activeGenre(genre)).toBeTruthy();
    });

    it('should return false when the genre is not the selectedGenre', () => {
      const controller = genreFilterCtrl();
      const genre = { genreName: genres[0] };

      expect(controller.activeGenre(genre)).toBeFalsy();
    });
  });

  describe('applyFilterByGenre', () => {
    let controller, genre;

    beforeEach(() => {
      controller = genreFilterCtrl();
      genre = { genreName: genres[0] };

      controller.applyFilterByGenre(genre);
    });

    it('should update the search query', () => {
      expect($location.search).toHaveBeenCalledWith('genre', genre);
    });

    it('should update the selected genre', () => {
      expect(controller.selectedGenre).toEqual(genre);
    });

    it('should filter by the genre', () => {
      expect(filterModel.filter).toHaveBeenCalledWith({genre: genre}, listener);
    });
  });

});
