import defaultHomePageTemplate from "./template.html";

function defaultContainerHomePageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/",
    name: "default.home",
    template: defaultHomePageTemplate
  });
}

export default defaultContainerHomePageConfig;
