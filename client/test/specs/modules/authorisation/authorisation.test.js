/* global describe, it, expect, beforeEach, jasmine, inject */
import angular from 'angular';
import authorisationModule from 'modules/authorisation/module';

describe('authorisation factory', () => {
  const loginCredentials = { login: 'login', password: 'password' };
  const session = { data: { sessionToken: 'sessionToken' } };

  /** @type {authorisation} */
  let authorisation;
  let $cookies;
  let sessionToken;
  let loginStore;
  let logoutStore;

  beforeEach(() => {
    angular.mock.module(authorisationModule, ($provide) => {
      $cookies = jasmine.createSpyObj('$cookies', ['get', 'put', 'remove']);
      sessionToken = jasmine.createSpyObj('sessionToken', ['set', 'has', 'destroy']);
      loginStore = jasmine.createSpyObj('loginStore', ['push']);
      logoutStore = jasmine.createSpyObj('logoutStore', ['push']);
      $provide.value('$cookies', $cookies);
      $provide.value('sessionToken', sessionToken);
      $provide.value('loginStore', loginStore);
      $provide.value('logoutStore', logoutStore);
    });

    inject((_authorisation_) => {
      authorisation = _authorisation_;
    });
  });

  it('should throw authorise error if not credentials passed', (done) => {
    (async () => {
      const reject = jasmine.createSpy('reject');
      await authorisation.authorise({}).then(null, reject);
      expect(reject).toHaveBeenCalled();
      done();
    })();
  });

  it('should not be authorised by default', () => {
    sessionToken.has.and.returnValue(undefined);
    expect(authorisation.isAuthorised()).toBeFalsy();
  });

  it('should authorise and save session', (done) => {
    (async () => {
      const credentials = [loginCredentials.login, loginCredentials.password];
      sessionToken.has.and.returnValue('sessionToken');
      loginStore.push.and.returnValue(Promise.resolve(session.data));
      await authorisation.authorise(...credentials);
      expect(authorisation.isAuthorised()).toBeTruthy();
      expect(loginStore.push).toHaveBeenCalledWith(...credentials);
      expect(sessionToken.set).toHaveBeenCalledWith('sessionToken');
      done();
    })();
  });

  it('should authorize and destroy session', (done) => {
    (async () => {
      sessionToken.has.and.returnValue('sessionToken');
      loginStore.push.and.returnValue(Promise.resolve(session.data));
      await authorisation.authorise(loginCredentials.login, loginCredentials.password);
      expect(authorisation.isAuthorised()).toBeTruthy();
      logoutStore.push.and.returnValue(Promise.resolve());
      await authorisation.destroy();
      expect(logoutStore.push).toHaveBeenCalled();
      expect(sessionToken.destroy).toHaveBeenCalled();
      done();
    })();
  });
});
