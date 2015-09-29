/* global describe, it, expect, beforeEach, afterEach, inject, jasmine */

import angular from 'angular';
import layoutPage from 'layout/layout.module.js';

describe('layout routes', () => {
  let state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);

    inject(($state, $rootScope) => {
      state = $state.get('msl');
    });
  });

  it('should use the layout template', () => {
    expect(state.template).toEqual(require('layout/layout.html'));
  });
});
