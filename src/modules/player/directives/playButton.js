import playButtonTemplate from "../templates/playButton.html";

function playButtonController ($scope, player) {
  "ngInject";

  this.currentlyPlayingSong = false;

  var onPlayerStateChange = () => {
    var songEntity = player.getSongEntity();
    if (songEntity) {
      this.currentlyPlayingSong = songEntity.songId === $scope.songId;
    } else {
      this.currentlyPlayingSong = false;
    }
    $scope.$evalAsync();
  };

  this.play = function () {
    if (this.currentlyPlayingSong) {
      player.stop();
    } else {
      player.play($scope.songId);
    }
  };

  player.addStateChangeListener(onPlayerStateChange);
  $scope.$watch("size", size => this.size = size);
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
