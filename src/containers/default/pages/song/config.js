import defaultSongPageTemplate from "./template.html";

function defaultContainerSongPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/song/:songId",
    name: "default.song",
    template: defaultSongPageTemplate
  });
}

export default defaultContainerSongPageConfig;
