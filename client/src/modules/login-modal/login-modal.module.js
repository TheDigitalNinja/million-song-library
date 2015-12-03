import './stylesheets/default.scss';
import angular from 'angular';
import loginModalCtrl from './login-modal.controller.js';
import loginModal from './login-modal';
import angularMaterial from 'angular-material';

export default angular.module('msl.loginModal', [angularMaterial])
  .service('loginModal', loginModal)
  .value('loginModalCtrl', loginModalCtrl)
  .name;
