import angular from 'angular';
import angularCookies from 'angular-cookies';
import authentication from './authentication';
import authCookie from './authCookie';

export default angular.module('msl.authentication', [angularCookies])
  .factory('authentication', authentication)
  .factory('authCookie', authCookie)
  .name;
