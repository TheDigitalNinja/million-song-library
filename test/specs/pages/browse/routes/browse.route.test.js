/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import browsePage from 'pages/browse/browse.module.js';

describe('browse route', () => {
  let state, $state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(browsePage);

    inject((_$state_, $rootScope) => {
      $state = _$state_;
      $state.go('msl.browse', { artist: 1, genre: 'rock', rating: 2 });
      $rootScope.$digest();
      state = $state.current;
    });
  });

  it('should include the query params', () => {
    expect($state.href(state)).toEqual(`#/browse?genre=rock&artist=1&rating=2`);
  });

  it('should use the browse template', () => {
    expect(state.template).toEqual(require('pages/browse/browse.html'));
  });

  it('should use the browse controller', () => {
    expect(state.controller).toEqual('browseCtrl');
  });

  it('should use the controller as vm', () => {
    expect(state.controllerAs).toEqual('vm');
  });

  it('should have the title browse', () => {
    expect(state.title).toEqual('Browse');
  });
});
