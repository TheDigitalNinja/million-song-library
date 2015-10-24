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
      $state = jasmine.createSpyObj('$state', ['go', 'current']);
      authorisation = jasmine.createSpyObj('authorisation', [
        'destroy',
        'isAuthorised',
        'addChangeListener',
        'removeChangeListener',
        'getUserData',
        ]);
      $provide.value('$state', $state);
      $provide.value('authorisation', authorisation);

      $state.current.and.returnValue({name: 'state'});
    });

    inject((_$controller_, $rootScope) => {
      $controller = _$controller_;
      $scope = $rootScope.$new();
    });
  });

  it('should change authorise flag when state changed and change current state', (done) => {
    (async () => {
      const navbarCtrl = $controller('navbarCtrl', { $scope });

      authorisation.destroy.and.returnValue(Promise.resolve());
      await navbarCtrl.logout();
      expect(authorisation.destroy).toHaveBeenCalled();
      expect($state.go).toHaveBeenCalledWith('msl.login');
      done();
    })();
  });

  it('should change authorised scope data', () => {
    const navbarCtrl = $controller('navbarCtrl', { $scope });
    expect(authorisation.addChangeListener).toHaveBeenCalledWith(navbarCtrl.onStateChange);
  });

  it('should not call change listener when state changes after scope is destroyed', () => {
    const navbarCtrl = $controller('navbarCtrl', { $scope });
    $scope.$destroy();
    expect(authorisation.removeChangeListener).toHaveBeenCalledWith(navbarCtrl.onStateChange);
  });

  describe('onStateChange', () => {
    let navbarCtrl;

    beforeEach(() => {
      navbarCtrl = $controller('navbarCtrl', { $scope });
    });

    describe('when is not authorised', () => {
      beforeEach(() => {
        authorisation.isAuthorised.and.returnValue(false);
        navbarCtrl.onStateChange();
      });

      it('should set the authorised property to false', () => {
        expect(navbarCtrl.authorised).toBeFalsy();
      });

      it('should not get user data', () => {
        expect(authorisation.getUserData).not.toHaveBeenCalled();
      });
    });

    describe('when it is authorised', () => {
      beforeEach(() => {
        authorisation.isAuthorised.and.returnValue(true);
        authorisation.getUserData.and.returnValue('login@email');
        navbarCtrl.onStateChange();
      });

      it('should set the authorised property to true', () => {
        expect(navbarCtrl.authorised).toBeTruthy();
      });

      it('should get user data', () => {
        expect(authorisation.getUserData).toHaveBeenCalledWith('userEmail');
      });

      it('should set the email', () => {
        expect(navbarCtrl.email).toBe('login@email');
      });
    });
  });
});
