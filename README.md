# Million Songs Library

## Developers

Project is based on [Webpack](http://webpack.github.io/) and [Angular](https://angularjs.org/).
Main `index.html` file is build by using webpacks [HTML Webpack Plugin](https://github.com/ampedandwired/html-webpack-plugin).
Project is based on ES6 syntax, it is converted to ES5 by using [BabelJS](https://babeljs.io) compiler with state `1`,
this means that we can you extra features like `await` and `async`. For stylesheets we use [LESS](http://lesscss.org/)
compiler.

### Installation

When you checkout the project for the first time run `npm install`, this will install `npm`
dependencies as well as it will trigger `bower install` for installing bower dependencies.

### Testing

Project is tested with `karma` with `jasmine` framework and `eslint` for reporting on patterns. You can run tests and
pattern reporting by simply calling `npm test`. Note that if `eslint` fails then tests will not be triggered.
You can also use `npm run autotest` for automated test runs when some source files changes.

### Build

Project is build by calling `npm run build`, this will create new source files and place them in `./build` directory.
Mount them on any server and you are ready to go or alternatively use `npm run serve-dev`.

### Dev Server

Start dev server by calling `npm run serve-dev`, this will automatically mound a server on `3000` localhost port.
If any changed will be made during dev server run, source files will be automatically rebuild.

### Prod Server

Start prod server by calling `npm run serve-prod`, this will try to mound `./build` directory to `80` port by
using [http-server](https://github.com/indexzero/http-server) for serving static resources. Before you run this make
sure you have called `npm run build` first.

### Environment

Webpack is running with `webpack.EnvironmentPlugin`, this means that we can define custom build environments, for e.g.
if you insert `process.env.NODE_ENV` into your code it will be replaced with build environment `development` or `production`
- this way you can do custom actions which are only required for specified environment.
