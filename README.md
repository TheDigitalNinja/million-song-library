# Million Songs Library

## Developers

Project is based on [Webpack](http://webpack.github.io/) and [Angular](https://angularjs.org/).
Main `index.html` file is build by using webpack's [HTML Webpack Plugin](https://github.com/ampedandwired/html-webpack-plugin).
Project is based on ES6 syntax, it is converted to ES5 by using [BabelJS](https://babeljs.io) compiler with stage `1`,
this means that we can use extra features like `await` and `async`. For stylesheets we use [LESS](http://lesscss.org/)
compiler.

### Installation

When you checkout the project for the first time run `npm install`, this will install `npm`
dependencies as well as it will trigger `bower install` for installing bower dependencies.

### Testing

Project is tested with `karma` with `jasmine` framework and `eslint` for reporting on patterns. You can run tests and
pattern reporting by simply calling `npm run test`. Note that if `eslint` fails then tests will not be triggered.
You can also use `npm run autotest` for automated test runs when some source files changes.

#### Functional Testing

Project is tested with `jasmine` using `protractor` framework. First, you need to start webdriver-manager
by calling `npm run webdriver-start`, and then you can run the tests by calling `npm run protractor`.
The tests will run in the different browsers(Chrome, Firefox, Safari and Internet Explorer).

### Build

Project is build by calling `npm run build`, this will create new source files and place them in `./build` directory,
also this will create Styleguide of the project.
Mount them on any server and you are ready to go or alternatively use `npm run serve-dev`.

### Dev Server

Start dev server by calling `npm run serve-dev`, this will automatically mound a server on `3000` localhost port.
If any change is made during dev server run, source files will be automatically rebuild.

### Prod Server

Start prod server by calling `npm run serve-prod`, this will try to mound `./build` directory to `80` port by
using [http-server](https://github.com/indexzero/http-server) for serving static resources. Before you run this make
sure you have called `npm run build` first.

### Styleguide

To create a styleguide of the project run `npm run styleguide`, this will
generate styleguide's files using [Kss-node](https://github.com/kss-node/kss-node)
in `/build/styleguide/` directory.

### Environment

Webpack is running with `webpack.EnvironmentPlugin`, this means that we can define custom build environments, for e.g.
if you insert `process.env.NODE_ENV` into your code it will be replaced with build environment `development` or `production`,
this way you can do custom actions which are only required for specified environment.

### File Layout Structure

#### Source Files

Source files are stored in `./src` directory. When webpack start build it starts from `./src/index.js` file.
Library files from `npm` and `bower` can be accessed by simple imports like in `node.js`. We strongly suggest
to use `npm` dependencies and use `bower` for dependencies that are not published to `npm`.

```
├──  /src
│   ├── /containers
│   │   │   # containers are layouts, different layouts have different controllers,
│   │   │   # pages, factories, stylesheets and other e.g. header controller might
│   │   │   # be different then in other layouts. Each container is a angular module
│   │   │   # with its custom config and dependencies. When using ui router you will
│   │   │   # define layout global states in container config.
│   │   ├── /<containers list...>
│   │   │   ├── /controllers
│   │   │   ├── /factories
│   │   │   ├── /pages
│   │   │   │   │   # container pages can have its controllers, factories, stylesheets
│   │   │   │   │   # and other. Each page is a different angular module so it can
│   │   │   │   │   # have custom config. When using angular ui router you will
│   │   │   │   │   # define it states in page module config - not in the global one!
│   │   │   │   │   # this way you get a clear view on page dependencies.
│   │   │   │   └── /<container pages list...>
│   │   │   │       ├── /controllers
│   │   │   │       ├── /factories
│   │   │   │       ├── /stylesheets
│   │   │   │       ├── /config.js
│   │   │   │       ├── /module.js
│   │   │   │       └── /template.html
│   │   │   ├── /stylesheets
│   │   │   ├── /config.js
│   │   │   ├── /run.js
│   │   │   ├── /layout.html
│   │   │   └── /module.js
│   │   └── /module.js
│   ├── /modules
│   │   │   # place to put custom modules used all over the system
│   │   └── /<modules list...>
│   ├── /config.js
│   ├── /run.js
│   └── /index.js
```

##### Ng Annotate

Make sure that you use `"ngInject";` for angular injectable dependencies.
Please read [ES6 support for ngAnnotate](https://github.com/olov/ng-annotate#es6-and-typescript-support).

### Test Files

Test files are stored in `./test/specs` directory. When karma runner starts it includes files by
`./test/specs/**/*.test.js` pattern only. Other files like source files that needs to be tested has to be
imported in the test files.

### API

In order to get API host add `process.env.API_HOST`. This will return host if it is defined at build time.

### Swagger

Swagger config file is placed in `./swagger/api/swagger/swagger.yaml`

#### Swagger Mock Server

Start mock server by running `npm run serve-mock`. **Note** that if you want that your dev server would
use swagger mock server you need to start it with `--mock` argument, for e.g. `npm run serve-dev --mock`.

#### Swagger Docs Editor

Start docs editor by running `npm run serve-docs`.
