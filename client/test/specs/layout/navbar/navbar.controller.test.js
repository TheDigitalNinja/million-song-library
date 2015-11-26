/* global describe, it, expect, beforeEach, inject, jasmine */
import angular from 'angular';
import navbarModule from 'layout/navbar/navbar.module.js';

describe('navbarCtrl', () => {
  let $controller;
  let $scope;
  let $state;
  let authorisation;

  beforeEach(() => {
    angular.mock.module(navbarModule, ($provide) => {
      $state = jasmine.createSpyObj('$state', ['go', 'current', 'is']);
      authorisation = jasmine.createSpyObj('authorisation', [
        'destroy',
        'isAuthorised',
        'addChangeListener',
        'removeChangeListener',
        'getUserData',
        ]);
      $provide.value('$state', $state);
      $provide.value('authorisation', authorisation);

      $state.current.and.returnValue({ name: 'state' });
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  it('should change authorise flag when state changed and change current state', (done) => {
    (async () => {
      const navbarCtrl = $controller('navbarCtrl', { $scope });
      $state.is.and.returnValue(false);
      await navbarCtrl.logout();
      expect(authorisation.destroy).toHaveBeenCalled();
      expect($state.go).toHaveBeenCalledWith('msl.login');
      done();
    })();
  });

  it('should redirect to home page on logout if state is msl.library', (done) => {
    (async () => {
      const navbarCtrl = $controller('navbarCtrl', { $scope });
      $state.is.and.returnValue(true);
      await navbarCtrl.logout();
      expect($state.go).toHaveBeenCalledWith('msl.home');
      done();
    })();
  });

});
