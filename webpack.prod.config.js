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
config.plugins.push(new ngAnnotatePlugin());
// find ExtractTextPlugin plugin and replace with one that used hash suffix
config.plugins = _.map(config.plugins, function (plugin) {
  if (plugin instanceof ExtractTextPlugin) {
    return new ExtractTextPlugin("stylesheet.[hash].css", {allChunks: true});
  }
  return plugin;
});
// find less loader and replace it with new loader that dose not uses source maps
config.module.loaders = _.map(config.module.loaders, function (loader) {
  if (loader.test.toString() === /\.less/.toString()) {
    return {test: /\.less/, loader: ExtractTextPlugin.extract("style-loader", "css-loader!less-loader")}
  }
  return loader;
});

module.exports = config;
