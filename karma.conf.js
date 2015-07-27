var _ = require("lodash");
var webpackConfig = require("./webpack.config.js");
// use global angular because its already included in karma runner
var webpackExternals = webpackConfig.externals;
webpackExternals.push({"angular": "angular"});

module.exports = function (config) {
  config.set({
    basePath: "",
    frameworks: ["jasmine"],
    files: [
      "./node_modules/angular/angular.js",
      "./node_modules/angular-mocks/angular-mocks.js",
      "./test/**/*.test.js"
    ],
    preprocessors: {
      "./test/**/*.test.js": ["webpack"]
    },
    webpack: _.assign(
      _.pick(webpackConfig, ["module", "resolve", "plugins"]), {
        externals: webpackExternals
      }
    ),
    webpackServer: {
      noInfo: true
    },
    plugins: [
      require("karma-webpack"),
      require("karma-jasmine"),
      require("karma-phantomjs-launcher")
    ],
    reporters: ["dots"],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ["PhantomJS"],
    singleRun: false
  });
};
