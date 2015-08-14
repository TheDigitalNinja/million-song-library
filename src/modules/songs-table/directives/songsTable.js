import songsTableTemplate from "../templates/songsTable.html";

function songsTable () {
  "ngInject";
  return {
    restrict: "E",
    template: songsTableTemplate,
    scope: {
      loading: "=",
      content: "="
    }
  };
}

export default songsTable;
