/* global describe, it, expect, beforeEach, inject, jasmine */

import angular from 'angular';
import errorPage from 'pages/error/error.module.js';

describe('error route', () => {
  let state;

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(errorPage);

    inject(($state) => {
      state = $state.get('error');
    });
  });

  it('should use error template', () => {
    expect(state.template).toEqual(require('pages/error/error.html'));
  });

  it('should have the error title', () => {
    expect(state.title).toEqual('Error');
  });
});
