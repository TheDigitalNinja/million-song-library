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
      $state = jasmine.createSpyObj('$state', ['go']);
      authorisation = jasmine.createSpyObj('authorisation', [
        'destroy',
        'isAuthorised',
        'addChangeListener',
        'removeChangeListener',
        'getUserData',
        ]);
      $provide.value('$state', $state);
      $provide.value('authorisation', authorisation);
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
    let bind, navbarCtrl;

    authorisation.addChangeListener.and.callFake((cb) => bind = cb);
    navbarCtrl = $controller('navbarCtrl', { $scope });
    authorisation.isAuthorised.and.returnValue(false);
    bind();
    expect(navbarCtrl.authorised).toBeFalsy();
    expect(authorisation.getUserData).not.toHaveBeenCalled();
    expect();
    authorisation.isAuthorised.and.returnValue(true);
    authorisation.getUserData.and.returnValue('login@email');
    bind();
    expect(navbarCtrl.authorised).toBeTruthy();
    expect(authorisation.getUserData).toHaveBeenCalledWith('userEmail');
    expect(navbarCtrl.email).toBe('login@email');
  });

  it('should not call change listener when state changes after scope is destroyed', () => {
    let bind, navbarCtrl;

    authorisation.addChangeListener.and.callFake((cb) => bind = cb);
    navbarCtrl = $controller('navbarCtrl', { $scope });
    authorisation.isAuthorised.and.returnValue(false);
    bind();
    expect(navbarCtrl.authorised).toBeFalsy();
    $scope.$destroy();
    expect(authorisation.removeChangeListener).toHaveBeenCalledWith(bind);
  });
});
