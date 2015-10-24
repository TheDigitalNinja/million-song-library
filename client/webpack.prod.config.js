var _ = require("lodash");
var webpack = require("webpack");
//var LessPluginCleanCSS = require('less-plugin-clean-css');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var CleanPlugin = require('clean-webpack-plugin');
var config = require("webpack.config.js");

// remove source maps
delete config.devtool;
// add eslint on error fail
config.eslint = {failOnError: true, failOnWarning: true};
// add clean css plugin
//config.lessLoader = {lessPlugins: [new LessPluginCleanCSS({advanced: true})]};
// set output file to use hash
config.output.filename = "build.js";
// add source uglify plugin
config.plugins.push(new webpack.optimize.UglifyJsPlugin({compress: {warnings: false}}));
// remove previous build files
config.plugins.push(new CleanPlugin(["build"]));
// find ExtractTextPlugin plugin and replace with one that used hash suffix
config.plugins = _.map(config.plugins, function (plugin) {
  if (plugin instanceof ExtractTextPlugin) {
    return new ExtractTextPlugin("stylesheet.css", {allChunks: true});
  }
  return plugin;
});
// find scss and css loaders and replace them with new loaders that does not use source maps
config.module.loaders = _.map(config.module.loaders, function (loader) {
  switch (loader.test.toString()) {
    case /\.css/.toString():
      return {test: /\.css/, loader: ExtractTextPlugin.extract("style", "css")};
    case /\.scss/.toString():
      return {test: /\.scss/, loader: ExtractTextPlugin.extract("style", "css!scss")};
  }
  return loader;
});

module.exports = config;
