import defaultSongPageTemplate from "./template.html";

function defaultContainerSongPageConfig ($stateProvider) {
  "ngInject";
  $stateProvider.state({
    url: "/song",
    name: "default.song",
    template: defaultSongPageTemplate
  });
}

export default defaultContainerSongPageConfig;
