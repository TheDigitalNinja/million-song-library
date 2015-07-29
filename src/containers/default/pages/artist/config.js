import defaultArtistPageTemplate from "./template.html";

function defaultContainerArtistPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/artist",
    name: "default.artist",
    template: defaultArtistPageTemplate
  });
}

export default defaultContainerArtistPageConfig;
