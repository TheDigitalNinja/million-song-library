import defaultSomePageTemplate from "./template.html";

function defaultContainerSomePageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/some",
    name: "default.some",
    template: defaultSomePageTemplate
  });
}

export default defaultContainerSomePageConfig;
