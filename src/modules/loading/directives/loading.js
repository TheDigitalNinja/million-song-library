import loadingTemplate from "../templates/loading.html";

function loadingDirective () {
  "ngInject";

  return {
    restrict: "A",
    template: loadingTemplate
  };
}

export default loadingDirective;
