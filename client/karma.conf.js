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
      "node_modules/es5-shim/es5-shim.js",
      "node_modules/angular/angular.js",
      "node_modules/angular-mocks/angular-mocks.js",
      "test/**/*.test.js"
    ],
    reporters: ["progress", "coverage"],
    preprocessors: {
      "./test/**/*.test.js": ["webpack", "sourcemap"],
    },
    coverageReporter: {
      type : 'html',
      dir : 'coverage/'
    },
    instrumenters: {isparta: require('isparta')},
    instrumenter: {
      './test/**/*.test.js': 'isparta'
    },
    webpack: _.assign(
      _.pick(webpackConfig, ["module", "resolve", "plugins"]), {
        externals: webpackExternals,
        devtool: "inline-source-map"
      }
    ),
    webpackServer: {
      noInfo: true
    },
    plugins: [
      require("karma-sourcemap-loader"),
      require("karma-coverage"),
      require("karma-webpack"),
      require("karma-jasmine"),
      require("karma-phantomjs-launcher")
    ],
    port: 9876,
    colors: true,
    logLevel: config.LOG_INFO,
    autoWatch: true,
    browsers: ["PhantomJS"],
    singleRun: false,
    browserNoActivityTimeout: 20000,
  });
};
