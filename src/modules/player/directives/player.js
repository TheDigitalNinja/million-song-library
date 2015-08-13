import playerTemplate from "../templates/player.html";

function playerController ($scope, player) {
  "ngInject";

  var onPlayerStateChange = () => {
    this.active = player.isActive();
    this.songEntity = player.getSongEntity();
    $scope.$evalAsync();
  };

  this.close = () => player.stop();

  player.addStateChangeListener(onPlayerStateChange);
  $scope.$on("$destroy", () => player.removeStateChangeListener(onPlayerStateChange));
}

function playerDirective () {
  "ngInject";

  return {
    restrict: "E",
    template: playerTemplate,
    controller: playerController,
    controllerAs: "player"
  };
}

export default playerDirective;
