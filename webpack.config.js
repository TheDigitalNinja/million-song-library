var _ = require("lodash");
var path = require("path");
var argv = require("yargs").argv;
var webpack = require("webpack");
var HtmlWebpackPlugin = require("html-webpack-plugin");
var ExtractTextPlugin = require("extract-text-webpack-plugin");

var context = path.join(__dirname, "src");
var build = path.join(__dirname, "build");
var exclude = /node_modules|bower_components/;

/**
 * If environment is not defined then check if it is not passed by argument.
 * Environment is passed by arguments because windows and unix handled environment
 * variables differently.
 */
if (_.isUndefined(process.env.NODE_ENV)) {
  process.env.NODE_ENV = argv.development ? "development" : "production";
}

module.exports = {
  context: context,
  entry: "./index.js",
  output: {
    path: build,
    filename: "build.js"
  },
  externals: [
    {"window": "window"},
    {"document": "document"}
  ],
  resolve: {
    modulesDirectories: ["node_modules", "bower_components", "src"]
  },
  devtool: "source-map",
  module: {
    preLoaders: [
      {test: /\.js$/, loader: "eslint-loader", exclude: exclude}
    ],
    loaders: [
      {test: /\.js$/, exclude: exclude, loader: "ng-annotate?add=true!babel?stage=1&optional=runtime"},
      {test: /\.html$/, loader: "html"},
      {test: /\.eot|ttf|woff|woof2|svg/, loader: "file"},
      {test: /\.css/, loader: ExtractTextPlugin.extract("style", "css?sourceMap")},
      {test: /\.less/, loader: ExtractTextPlugin.extract("style", "css?sourceMap!less?sourceMap")}
    ]
  },
  plugins: [
    new webpack.EnvironmentPlugin(["NODE_ENV"]),
    new HtmlWebpackPlugin({filename: "index.html", template: path.join(context, "index.tpl.html")}),
    new ExtractTextPlugin("stylesheet.css", {allChunks: true})
  ]
};
