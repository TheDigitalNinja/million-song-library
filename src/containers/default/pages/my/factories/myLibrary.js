import _ from "lodash";

function myLibrary ($http) {
  "ngInject";

  function withHost () {
    return [process.env.API_HOST].concat(_.toArray(arguments)).join("");
  }

  return {
    async fetch() {
      var content = (await $http.get(withHost("/api/accountedge/users/mylibrary"))).data;
      console.log(content);
    }
  };
}

export default myLibrary;
