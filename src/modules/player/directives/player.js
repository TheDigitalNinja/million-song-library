/**
 * player directive controller
 * @param {$rootScope.Scope} $scope
 * @param {player} player
 */
function playerController ($scope, player) {
  'ngInject';

  /**
   * listener for player state change
   * this is not angular action so we need to digest scope manually
   */
  let onPlayerStateChange = () => {
    this.active = player.isActive();
    this.songEntity = player.getSongEntity();
    $scope.$evalAsync();
  };

  /**
   * close player user action
   */
  this.close = () => player.stop();

  // add player state change listener
  player.addStateChangeListener(onPlayerStateChange);
  // when scope destroys then remove player state change listener
  $scope.$on('$destroy', () => player.removeStateChangeListener(onPlayerStateChange));
}

export default function playerDirective () {
  'ngInject';

  return {
    restrict: 'E',
    template: require('../templates/player.html'),
    controller: playerController,
    controllerAs: 'player',
  };
}
