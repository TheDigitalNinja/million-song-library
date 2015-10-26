/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import artistPage from 'pages/artist/artist.module.js';

describe('artist routes', () => {
  let state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(artistPage);

    inject(($state) => {
      state = $state.get('msl.artist');
    });
  });

  it('should use the artist template', () => {
    expect(state.template).toEqual(require('pages/artist/templates/artist.html'));
  });
});
