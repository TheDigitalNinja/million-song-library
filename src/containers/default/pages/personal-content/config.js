import defaultPersonalContentPageTemplate from "./template.html";

function defaultContainerPersonalContentPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/personal-content",
    name: "default.personalContent",
    template: defaultPersonalContentPageTemplate
  });
}

export default defaultContainerPersonalContentPageConfig;
