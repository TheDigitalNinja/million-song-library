import defaultLoginPageTemplate from "./template.html";

function defaultContainerLoginPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/login",
    name: "default.login",
    template: defaultLoginPageTemplate
  });
}

export default defaultContainerLoginPageConfig;
