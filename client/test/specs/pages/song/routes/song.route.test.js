/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import songPage from 'pages/song/song.module.js';

describe('song routes', () => {
  const songId = 2;
  let state;
  let $state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(songPage);

    inject((_$state_, $rootScope) => {
      $state = _$state_;
      $state.go('msl.song', { songId: songId });
      $rootScope.$digest();
      state = $state.current;
    });
  });

  it('should include the songId on the url', () => {
    expect($state.href(state)).toEqual(`#/song/${ songId }`);
  });

  it('should use the song template', () => {
    expect(state.template).toEqual(require('pages/song/song.html'));
  });

  it('should use song controller', () => {
    expect(state.controller).toEqual('songCtrl');
  });

  it('should use the controller as vm', () => {
    expect(state.controllerAs).toEqual('vm');
  });
});
