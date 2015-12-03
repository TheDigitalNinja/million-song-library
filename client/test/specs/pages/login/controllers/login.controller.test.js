/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import loginPage from 'pages/login/login.module.js';

describe('loginCtrl', () => {
  let $scope;
  let $state;
  let $controller;
  let authentication;

  beforeEach(() => {
    angular.mock.module(loginPage, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go']);
      authentication = jasmine.createSpyObj('authentication', ['authenticate']);
      $provide.value('$state', $state);
      $provide.value('authentication', authentication);
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  it('should authenticate with credentials', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authentication.authenticate.and.returnValue(Promise.resolve());
      loginCtrl.email = 'test@test.com';
      loginCtrl.password = 'password';
      await loginCtrl.submit();
      expect(authentication.authenticate).toHaveBeenCalledWith(loginCtrl.email, loginCtrl.password);
      expect($state.go).toHaveBeenCalledWith('msl.home');
      done();
    })();
  });

  it('should fail to authenticate and set hasError as true', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authentication.authenticate.and.returnValue(Promise.reject());
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBe(true);
      expect($state.go).not.toHaveBeenCalled();
      done();
    })();
  });

  it('should set hasError variable and then remove it', (done) => {
    (async () => {
      const loginCtrl = $controller('loginCtrl', { $scope });

      authentication.authenticate.and.returnValue(Promise.reject());
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBeTruthy();
      authentication.authenticate.and.returnValue(Promise.resolve());
      loginCtrl.email = 'test@test.com';
      loginCtrl.password = 'password';
      await loginCtrl.submit();
      expect(loginCtrl.hasError).toBeUndefined();
      expect(authentication.authenticate).toHaveBeenCalledWith(loginCtrl.email, loginCtrl.password);
      expect($state.go).toHaveBeenCalledWith('msl.home');
      done();
    })();
  });
});
