import angular from 'angular';
import angularCookies from 'angular-cookies';
import authentication from './authentication';
import authCookie from './authCookie';
import password from './directives/password.js';

export default angular.module('msl.authentication', [angularCookies])
  .directive('password', password)
  .factory('authentication', authentication)
  .factory('authCookie', authCookie)
  .name;
