var _ = require("lodash");
var path = require("path");
var argv = require("yargs").argv;
var webpack = require("webpack");
var HtmlWebpackPlugin = require("html-webpack-plugin");
var ExtractTextPlugin = require("extract-text-webpack-plugin");

var context = path.join(__dirname, "src");
var build = path.join(__dirname, "client/build");
var exclude = /node_modules|bower_components/;

/**
 * If environment is not defined then check if it is not passed by argument.
 * Environment is passed by arguments because windows and unix handled environment
 * variables differently.
 */
if (_.isUndefined(process.env.NODE_ENV)) {
  process.env.NODE_ENV = argv.development ? "development" : "production";
}

/**
 * If api host environment is not defined and api mock param is set then
 * set api host environment as swagger host.
 */
if (_.isUndefined(process.env.API_HOST)) {
  if (argv.mock || process.env.npm_config_mock) {
    process.env.API_HOST = "http://localhost:10010";
  } else {
    process.env.API_HOST = "http://localhost:9000";
  }
}

module.exports = {
  context: context,
  entry: "index.js",
  output: {
    path: build,
    filename: "build.js"
  },
  externals: [
    {"window": "window"},
    {"document": "document"}
  ],
  resolve: {
    modulesDirectories: ["node_modules", "bower_components"],
    extensions: ["", ".js", ".json"],
    root: [context]
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
      {
          test: /\.scss$/,
          loader: ExtractTextPlugin.extract(
              // activate source maps via loader query
              'css?sourceMap!' +
              'sass?sourceMap'
          )
      },
      {
        test: /\.(jpe?g|png|gif|svg)$/i,
        loaders: [
            'file?hash=sha512&digest=hex&name=[hash].[ext]',
            'image-webpack?bypassOnDebug&optimizationLevel=7&interlaced=false'
        ]
      }
    ],
    postLoaders: [{
      test: /\.js$/,
      exclude: /(test|node_modules)\//,
      loader: 'isparta'
    }]
  },
  plugins: [
    new webpack.EnvironmentPlugin(["NODE_ENV", "API_HOST"]),
    // use jquery as a global because some libraries like bootstrap expects it to be global
    new webpack.ProvidePlugin({jQuery: "jquery"}),
    new HtmlWebpackPlugin({filename: "index.html", template: path.join(context, "index.tpl.html")}),
    new ExtractTextPlugin("stylesheet.css", {allChunks: true})
  ]
};
