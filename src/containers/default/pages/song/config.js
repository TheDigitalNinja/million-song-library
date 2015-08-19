import defaultSongPageTemplate from "./template.html";

/**
 * Angular config for Song page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerSongPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/song/:songId",
    name: "default.song",
    template: defaultSongPageTemplate
  });
}

export default defaultContainerSongPageConfig;
