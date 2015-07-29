import "./stylesheets/default.less";
import angular from "angular";
import router from "angular-ui-router";
import authorisation from "modules/authorisation/module";
import homePage from "./pages/home/module";
import loginPage from "./pages/login/module";
import artistPage from "./pages/artist/module";
import songPage from "./pages/song/module";
import personalContentPage from "./pages/my/module";
import defaultContainerConfig from "./config";
import headerCtrl from "./controllers/headerCtrl";

export default angular.module("msl.containers.default", [
  router,
  authorisation,
  homePage,
  loginPage,
  artistPage,
  songPage,
  personalContentPage
])
  .config(defaultContainerConfig)
  .controller("headerCtrl", headerCtrl)
  .name;
