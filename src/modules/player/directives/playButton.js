import playButtonTemplate from "../templates/playButton.html";

/**
 * play button directive controller
 * @param {$rootScope.Scope} $scope
 * @param {player} player
 */
function playButtonController ($scope, player) {
  "ngInject";

  this.currentlyPlayingSong = false;

  /**
   * listener for player sate change
   * this is not angular action so we need to digest scope manually
   */
  var onPlayerStateChange = () => {
    var songEntity = player.getSongEntity();
    if (songEntity) {
      this.currentlyPlayingSong = songEntity.songId === $scope.songId;
    } else {
      this.currentlyPlayingSong = false;
    }
    $scope.$evalAsync();
  };

  /**
   * play button user action
   */
  this.play = function () {
    if (this.currentlyPlayingSong) {
      player.stop();
    } else {
      player.play($scope.songId);
    }
  };

  // add player state change listener
  player.addStateChangeListener(onPlayerStateChange);
  // when scope destroys then remove player state change listener
  $scope.$on("$destroy", () => player.removeStateChangeListener(onPlayerStateChange));
}

function playButton () {
  "ngInject";
  return {
    restrict: "E",
    controller: playButtonController,
    controllerAs: "pb",
    template: playButtonTemplate,
    scope: {
      size: "@",
      songId: "="
    }
  };
}

export default playButton;
