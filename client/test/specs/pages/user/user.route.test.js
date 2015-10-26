/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import layoutPage from 'layout/layout.module.js';
import userPage from 'pages/user/user.module.js';
import {USER_REDIRECT_TO, ROLE_USER} from 'constants';

describe('user routes', () => {
  let $state;
  let $rootScope;

  const goTo = (state) => {
    $state.go(state);
    $rootScope.$digest();
  };

  beforeEach(() => {
    angular.mock.module('ui.router');
    angular.mock.module(layoutPage);
    angular.mock.module(userPage);

    inject((_$state_, _$rootScope_) => {
      $state = _$state_;
      $rootScope = _$rootScope_;
    });
  });

  describe('user state', () => {
    let userState;

    beforeEach(() => {
      goTo('msl.user');

      userState = $state.current;
    });

    it('should use the user template', () => {
      expect(userState.template).toEqual(require('pages/user/templates/user.html'));
    });

    it('should set the permission only to role user', () => {
      expect(userState.data.permissions.only).toContain(ROLE_USER);
    });

    it('should set the redirect to USER_REDIRECT_TO', () => {
      expect(userState.data.permissions.redirectTo).toEqual(USER_REDIRECT_TO);
    });
  });

  describe('user history state', () => {
    let historyState;

    beforeEach(() => {
      goTo('msl.user.history');

      historyState = $state.current;
    });

    it('should have /my/history as url', () => {
      expect($state.href(historyState)).toEqual('#/my/history');
    });

    it('should use the user-history template', () => {
      expect(historyState.template).toEqual(require('pages/user/templates/user-history.html'));
    });

    it('should the userHistoryCtrl controller', () => {
      expect(historyState.controller).toEqual('userHistoryCtrl');
    });

    it('should named the controller as vm', () => {
      expect(historyState.controllerAs).toEqual('vm');
    });
  });

  describe('user likes state', () => {
    let likesState;

    beforeEach(() => {
      goTo('msl.user.likes');

      likesState = $state.current;
    });

    it('should have /my/likes as url', () => {
      expect($state.href(likesState)).toEqual('#/my/likes');
    });

    it('should use the user-likes template', () => {
      expect(likesState.template).toEqual(require('pages/user/templates/user-likes.html'));
    });

    it('should the userLikesCtrl controller', () => {
      expect(likesState.controller).toEqual('userLikesCtrl');
    });

    it('should named the controller as vm', () => {
      expect(likesState.controllerAs).toEqual('vm');
    });
  });

  describe('user libray state', () => {
    let libraryState;

    beforeEach(() => {
      goTo('msl.user.library');

      libraryState = $state.current;
    });

    it('should have /my/library as url', () => {
      expect($state.href(libraryState)).toEqual('#/my/library');
    });

    it('should use the user-library template', () => {
      expect(libraryState.template).toEqual(require('pages/user/templates/user-library.html'));
    });

    it('should the userLibraryCtrl controller', () => {
      expect(libraryState.controller).toEqual('userLibraryCtrl');
    });

    it('should named the controller as vm', () => {
      expect(libraryState.controllerAs).toEqual('vm');
    });
  });
});
