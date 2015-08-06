import "./stylesheets/default.less";
import angular from "angular";
import router from "angular-ui-router";
import authorisation from "modules/authorisation/module";
import player from "modules/player/module";
import homePage from "./pages/home/module";
import loginPage from "./pages/login/module";
import artistPage from "./pages/artist/module";
import songPage from "./pages/song/module";
import searchPage from "./pages/search/module";
import personalContentPage from "./pages/my/module";
import browsePage from "./pages/browse/module";
import defaultContainerConfig from "./config";
import headerCtrl from "./controllers/headerCtrl";
import headerSearchCtrl from "./controllers/headerSearchCtrl";

export default angular.module("msl.containers.default", [
  router,
  authorisation,
  player,
  homePage,
  loginPage,
  artistPage,
  songPage,
  personalContentPage,
  searchPage,
  browsePage
])
  .config(defaultContainerConfig)
  .controller("headerCtrl", headerCtrl)
  .controller("headerSearchCtrl", headerSearchCtrl)
  .name;
