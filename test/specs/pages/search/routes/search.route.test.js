/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import searchPage from 'pages/search/search.module.js';

describe('search routes', () => {
  const searchQuery = 'pop';
  let state;
  let $state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(searchPage);

    inject((_$state_, $rootScope) => {
      $state = _$state_;
      $state.go('msl.search', { query: searchQuery });
      $rootScope.$digest();
      state = $state.current;
    });
  });

  it('should include the searchId on the url', () => {
    expect($state.href(state)).toEqual(`#/search/${ searchQuery }`);
  });

  it('should use the search template', () => {
    expect(state.template).toEqual(require('pages/search/search.html'));
  });

  it('should use search controller', () => {
    expect(state.controller).toEqual('searchCtrl');
  });

  it('should use the controller as vm', () => {
    expect(state.controllerAs).toEqual('vm');
  });
});
