/* global describe, beforeEach, inject, it, expect, jasmine */
import angular from 'angular';
import authenticationModule from 'modules/authentication/module';

describe('authCookie', () => {
  let $cookies;
  let authCookie;

  beforeEach(() => {
    angular.mock.module(authenticationModule, ($provide) => {
      $cookies = jasmine.createSpyObj('$cookies', ['put', 'get', 'remove']);
      $provide.value('$cookies', $cookies);
    });

    inject((_authCookie_) => {
      authCookie = _authCookie_;
    });
  });

  it('should set token to cookie', () => {
    authCookie.set('token');
    expect($cookies.put).toHaveBeenCalledWith('authenticated', 'token');
  });

  it('should get token from cookie', () => {
    $cookies.get.and.returnValue('token');
    expect(authCookie.get()).toBe('token');
    expect($cookies.get).toHaveBeenCalledWith('authenticated');
  });

  it('should have cookie data set', () => {
    $cookies.get.and.returnValue('token');
    expect(authCookie.has()).toBeTruthy();
    expect($cookies.get).toHaveBeenCalledWith('authenticated');
  });

  it('should remove cookie data', () => {
    authCookie.destroy();
    expect($cookies.remove).toHaveBeenCalledWith('authenticated');
  });
});
