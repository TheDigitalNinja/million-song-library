import defaultContainerTemplate from "./layout.html";

export default /*@ngInject*/ function defaultContainerConfig ($stateProvider) {
  $stateProvider.state({
    name: "default",
    template: defaultContainerTemplate
  });
}
