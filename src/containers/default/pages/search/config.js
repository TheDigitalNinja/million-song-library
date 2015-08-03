import defaultSearchPageTemplate from "./template.html";

function defaultContainerSearchPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/search/:query",
    name: "default.search",
    template: defaultSearchPageTemplate
  });
}

export default defaultContainerSearchPageConfig;
