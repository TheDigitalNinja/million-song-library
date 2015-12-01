/* global describe, it, expect, beforeEach, jasmine, inject */
import angular from 'angular';
import authenticationModule from 'modules/authentication/module';

describe('authentication factory', () => {
  const loginCredentials = { login: 'login', password: 'password' };
  const session = { data: { authenticated: 'SomeDate' } };

  /** @type {authentication} */
  let authentication;
  let $cookies;
  let authCookie;
  let loginStore;
  let logoutStore;

  beforeEach(() => {
    angular.mock.module(authenticationModule, ($provide) => {
      $cookies = jasmine.createSpyObj('$cookies', ['get', 'put', 'remove']);
      authCookie = jasmine.createSpyObj('authCookie', ['set', 'has', 'destroy']);
      loginStore = jasmine.createSpyObj('loginStore', ['push']);
      logoutStore = jasmine.createSpyObj('logoutStore', ['push']);
      $provide.value('$cookies', $cookies);
      $provide.value('authCookie', authCookie);
      $provide.value('loginStore', loginStore);
      $provide.value('logoutStore', logoutStore);
    });

    inject((_authentication_) => {
      authentication = _authentication_;
    });
  });

  it('should throw authenticate error if not credentials passed', (done) => {
    (async () => {
      const reject = jasmine.createSpy('reject');
      await authentication.authenticate({}).then(null, reject);
      expect(reject).toHaveBeenCalled();
      done();
    })();
  });

  it('should not be authorised by default', () => {
    authCookie.has.and.returnValue(undefined);
    expect(authentication.isAuthenticated()).toBeFalsy();
  });

  it('should authenticate and save session', (done) => {
    (async () => {
      const credentials = [loginCredentials.login, loginCredentials.password];
      authCookie.has.and.returnValue('authCookie');
      loginStore.push.and.returnValue(Promise.resolve(session.data));
      await authentication.authenticate(...credentials);
      expect(authentication.isAuthenticated()).toBeTruthy();
      expect(loginStore.push).toHaveBeenCalledWith(...credentials);
      expect(authCookie.set).toHaveBeenCalledWith('SomeDate');
      done();
    })();
  });

  it('should authenticate and destroy session', (done) => {
    (async () => {
      authCookie.has.and.returnValue('authCookie');
      loginStore.push.and.returnValue(Promise.resolve(session.data));
      await authentication.authenticate(loginCredentials.login, loginCredentials.password);
      expect(authentication.isAuthenticated()).toBeTruthy();
      logoutStore.push.and.returnValue(Promise.resolve());
      await authentication.destroy();
      expect(logoutStore.push).toHaveBeenCalled();
      expect(authCookie.destroy).toHaveBeenCalled();
      done();
    })();
  });
});
