var _ = require("lodash");
var webpack = require("webpack");
var ngAnnotatePlugin = require("ng-annotate-webpack-plugin");
var LessPluginCleanCSS = require('less-plugin-clean-css');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var config = require("./webpack.config.js");

// remove source maps
delete config.devtool;
// add eslint on error fail
config.eslint = {failOnError: true, failOnWarning: true};
// add clean css plugin
config.lessLoader = {lessPlugins: [new LessPluginCleanCSS({advanced: true})]};
// set output file to use hash
config.output.filename = "build.[hash].js";
// add source uglify plugin
config.plugins.push(new webpack.optimize.UglifyJsPlugin({compress: {warnings: false}}));
// add angular annotate plugin
config.plugins.push(new ngAnnotatePlugin({add: true}));
// find ExtractTextPlugin plugin and replace with one that used hash suffix
config.plugins = _.map(config.plugins, function (plugin) {
  if (plugin instanceof ExtractTextPlugin) {
    return new ExtractTextPlugin("stylesheet.[hash].css", {allChunks: true});
  }
  return plugin;
});
// find less and css loaders and replace them with new loaders that dose not uses source maps
config.module.loaders = _.map(config.module.loaders, function (loader) {
  switch (loader.test.toString()) {
    case /\.css/.toString():
      return {test: /\.css/, loader: ExtractTextPlugin.extract("style", "css")};
    case /\.less/.toString():
      return {test: /\.less/, loader: ExtractTextPlugin.extract("style", "css!less")};
  }
  return loader;
});

module.exports = config;
