/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import loginPage from 'pages/login/login.module.js';

describe('loginCtrl', () => {
  let $scope;
  let $state;
  let $controller;
  let authorisation;

  beforeEach(() => {
    angular.mock.module(loginPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      authorisation = jasmine.createSpyObj('authorisation', ['authorise']);
      $provide.value('$state', $state);
      $provide.value('authorisation', authorisation);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  it('should authorise with credentials', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authorisation.authorise.and.returnValue(Promise.resolve());
      loginCtrl.email = 'test@test.com';
      loginCtrl.password = 'password';
      await loginCtrl.submit();
      expect(authorisation.authorise).toHaveBeenCalledWith(loginCtrl.email, loginCtrl.password);
      expect($state.go).toHaveBeenCalledWith('msl.home');
      done();
    })();
  });

  it('should fail to authorise and set hasError as true', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authorisation.authorise.and.returnValue(Promise.reject());
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBe(true);
      expect($state.go).not.toHaveBeenCalled();
      done();
    })();
  });

  it('should set hasError variable and then remove it', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authorisation.authorise.and.returnValue(Promise.reject());
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBeTruthy();
      authorisation.authorise.and.returnValue(Promise.resolve());
      loginCtrl.email = 'test@test.com';
      loginCtrl.password = 'password';
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBeUndefined();
      expect(authorisation.authorise).toHaveBeenCalledWith(loginCtrl.email, loginCtrl.password);
      expect($state.go).toHaveBeenCalledWith('msl.home');
      done();
    })();
  });
});
