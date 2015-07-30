import defaultLoginPageTemplate from "./template.html";
import {ANONYMOUS_REDIRECT_TO, ROLE_ANONYMOUS} from "constants";

function defaultContainerLoginPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/login",
    name: "default.login",
    template: defaultLoginPageTemplate,
    data: {
      permissions: {
        only: [ROLE_ANONYMOUS],
        redirectTo: ANONYMOUS_REDIRECT_TO
      }
    }
  });
}

export default defaultContainerLoginPageConfig;
