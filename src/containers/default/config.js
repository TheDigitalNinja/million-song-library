import defaultContainerTemplate from "./layout.html";

/**
 * angular config for default container
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerConfig ($stateProvider) {
  "ngInject";

  // default state is used so we do not re-render elements like navigation
  // when user navigates to new default container page
  $stateProvider.state({
    name: "default",
    template: defaultContainerTemplate
  });
}

export default defaultContainerConfig;
