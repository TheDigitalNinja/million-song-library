/* global describe, beforeEach, inject, it, expect, jasmine */
import angular from 'angular';
import authorisationModule from 'modules/authorisation/module';

describe('sessionToken', () => {
  let $cookies;
  let sessionToken;

  beforeEach(() => {
    angular.mock.module(authorisationModule, ($provide) => {
      $cookies = jasmine.createSpyObj('$cookies', ['put', 'get', 'remove']);
      $provide.value('$cookies', $cookies);
    });

    inject((_sessionToken_) => {
      sessionToken = _sessionToken_;
    });
  });

  it('should set token to cookie', () => {
    sessionToken.set('token');
    expect($cookies.put).toHaveBeenCalledWith('sessionToken', 'token');
  });

  it('should get token from cookie', () => {
    $cookies.get.and.returnValue('token');
    expect(sessionToken.get()).toBe('token');
    expect($cookies.get).toHaveBeenCalledWith('sessionToken');
  });

  it('should have cookie data set', () => {
    $cookies.get.and.returnValue('token');
    expect(sessionToken.has()).toBeTruthy();
    expect($cookies.get).toHaveBeenCalledWith('sessionToken');
  });

  it('should remove cookie data', () => {
    sessionToken.destroy();
    expect($cookies.remove).toHaveBeenCalledWith('sessionToken');
  });
});
