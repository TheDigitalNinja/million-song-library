import './stylesheets/default.less';
import angular from 'angular';
import loadingDirective from './directives/loading';

export default angular.module('msl.loading', [])
  .directive('loading', loadingDirective)
  .name;
