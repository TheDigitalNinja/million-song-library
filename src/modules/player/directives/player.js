import playerTemplate from "../templates/player.html";

function playerController ($scope, player) {
  "ngInject";

  var onPlayerStateChange = () => {
    //this.active = player.isActive();
    this.active = true;
    $scope.$evalAsync();
  };

  this.close = () => {
    this.active = false;
  };

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
