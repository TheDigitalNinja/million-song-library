/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import homePage from 'pages/home/home.module.js';

describe('home routes', () => {
  let state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(homePage);

    inject(($state) => {
      state = $state.get('msl.home');
    });
  });

  it('should use the home template', () => {
    expect(state.template).toEqual(require('pages/home/home.html'));
  });
});
