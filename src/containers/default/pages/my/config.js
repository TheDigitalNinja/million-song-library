import defaultMyPageTemplate from "./template.html";
import defaultMyPageHistoryTemplate from "./templates/history.html";
import defaultMyPageLikesTemplate from "./templates/likes.html";
import defaultMyPageLibraryTemplate from "./templates/library.html";

function defaultContainerMyPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider
    .state({
      name: "default.my",
      template: defaultMyPageTemplate
    })
    .state({
      url: "/my/history",
      name: "default.my.history",
      template: defaultMyPageHistoryTemplate
    })
    .state({
      url: "/my/likes",
      name: "default.my.likes",
      template: defaultMyPageLikesTemplate
    })
    .state({
      url: "/my/library",
      name: "default.my.library",
      template: defaultMyPageLibraryTemplate
    });
}

export default defaultContainerMyPageConfig;
