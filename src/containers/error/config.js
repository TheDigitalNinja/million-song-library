import errorContainerTemplate from "./layout.html";

function errorContainerConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    name: "error",
    template: errorContainerTemplate
  });
}

export default errorContainerConfig;
