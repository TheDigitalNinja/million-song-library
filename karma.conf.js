var _ = require("lodash");
var webpackConfig = require("./webpack.config.js");

module.exports = function (config) {
  config.set({
    basePath: "",
    frameworks: ["jasmine"],
    files: [
      "./test/**/*.test.js"
    ],
    preprocessors: {
      "./test/**/*.test.js": ["webpack"]
    },
    webpack: _.pick(webpackConfig, ["externals", "module", "resolve", "plugins"]),
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
