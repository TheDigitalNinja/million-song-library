import defaultBrowsePageTemplate from "./template.html";

function defaultContainerBrowsePageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/browse",
    name: "default.browse",
    template: defaultBrowsePageTemplate
  });
}

export default defaultContainerBrowsePageConfig;
