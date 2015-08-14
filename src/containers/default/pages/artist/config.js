import defaultArtistPageTemplate from "./template.html";

function defaultContainerArtistPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/artist/:artistId",
    name: "default.artist",
    template: defaultArtistPageTemplate
  });
}

export default defaultContainerArtistPageConfig;
