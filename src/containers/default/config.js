import defaultContainerTemplate from "./layout.html";

function defaultContainerConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    name: "default",
    template: defaultContainerTemplate
  });
}

export default defaultContainerConfig;
