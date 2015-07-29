import "./stylesheets/default.less";
import angular from "angular";
import router from "angular-ui-router";
import authorisation from "authorisation/module";
import homePage from "./pages/home/module";
import somePage from "./pages/some/module";
import loginPage from "./pages/login/module";
import artistPage from "./pages/artist/module";
import songPage from "./pages/song/module";
import defaultContainerConfig from "./config";
import headerCtrl from "./controllers/headerCtrl";


export default angular.module("msl.containers.default", [
  router,
  authorisation,
  homePage,
  somePage,
  loginPage,
  artistPage,
  songPage
])
  .config(defaultContainerConfig)
  .controller("headerCtrl", headerCtrl)
  .name;
