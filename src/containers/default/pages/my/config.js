import defaultMyPageTemplate from "./templates/default.html";
import defaultMyPageHistoryTemplate from "./templates/history.html";
import defaultMyPageLikesTemplate from "./templates/likes.html";
import defaultMyPageLibraryTemplate from "./templates/library.html";
import {USER_REDIRECT_TO, ROLE_USER} from "constants";

/**
 * angular config for my page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerMyPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    name: "default.my",
    template: defaultMyPageTemplate,
    data: {
      permissions: {
        only: [ROLE_USER],
        redirectTo: USER_REDIRECT_TO
      }
    }
  }).state({
    url: "/my/history",
    name: "default.my.history",
    template: defaultMyPageHistoryTemplate
  }).state({
    url: "/my/likes",
    name: "default.my.likes",
    template: defaultMyPageLikesTemplate
  }).state({
    url: "/my/library",
    name: "default.my.library",
    template: defaultMyPageLibraryTemplate
  });
}

export default defaultContainerMyPageConfig;
