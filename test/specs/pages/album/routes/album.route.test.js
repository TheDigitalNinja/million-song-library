/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import albumPage from 'pages/album/album.module.js';

describe('album routes', () => {
  let state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(albumPage);

    inject(($state) => {
      state = $state.get('msl.album');
    });
  });

  it('should use the album template', () => {
    expect(state.template).toEqual(require('pages/album/templates/album.html'));
  });
});
