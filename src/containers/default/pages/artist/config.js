import defaultArtistPageTemplate from "./template.html";

/**
 * Angular config for Artist page
 * @param {ui.router.state.$stateProvider} $stateProvider
 */
function defaultContainerArtistPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/artist/:artistId",
    name: "default.artist",
    template: defaultArtistPageTemplate
  });
}

export default defaultContainerArtistPageConfig;
