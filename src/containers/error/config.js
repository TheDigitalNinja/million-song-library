import errorContainerTemplate from "./layout.html";

/**
 * angular config for error module
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function errorContainerConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    name: "error",
    template: errorContainerTemplate
  });
}

export default errorContainerConfig;
