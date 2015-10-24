import './stylesheets/default.scss';
import angular from 'angular';
import playerFactory from './factories/player';
import playerDirective from './directives/player';
import playButtonDirective from './directives/playButton';

export default angular.module('msl.player', [])
  .factory('player', playerFactory)
  .directive('player', playerDirective)
  .directive('playButton', playButtonDirective)
  .name;
