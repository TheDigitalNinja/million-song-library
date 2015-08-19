import defaultHomePageTemplate from "./template.html";

/**
 * Angular config for Home page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerHomePageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/",
    name: "default.home",
    template: defaultHomePageTemplate
  });
}

export default defaultContainerHomePageConfig;
