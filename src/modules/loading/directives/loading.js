import loadingTemplate from "../templates/loading.html";

function loadingDirective () {
  "ngInject";

  return {
    restrict: "E",
    template: loadingTemplate
  };
}

export default loadingDirective;
