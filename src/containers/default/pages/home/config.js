import defaultHomePageTemplate from "./template.html";

export default /*@ngInject*/ function defaultContainerHomePageConfig ($stateProvider) {
  $stateProvider.state({
    url: "/",
    name: "default.home",
    template: defaultHomePageTemplate
  });
}
