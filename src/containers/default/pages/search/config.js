import defaultSearchPageTemplate from "./template.html";

/**
 * Angular config for Search page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerSearchPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/search/:query",
    name: "default.search",
    template: defaultSearchPageTemplate
  });
}

export default defaultContainerSearchPageConfig;
