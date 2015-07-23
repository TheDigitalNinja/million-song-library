import defaultSomePageTemplate from "./template.html";

export default /*@ngInject*/ function defaultContainerSomePageConfig ($stateProvider) {
  $stateProvider.state({
    url: "/some",
    name: "default.some",
    template: defaultSomePageTemplate
  });
}
