# Million Songs Library

## Developers

Project is based on [Webpack](http://webpack.github.io/) and [Angular](https://angularjs.org/).
Main `index.html` file is built by using webpack's [HTML Webpack Plugin](https://github.com/ampedandwired/html-webpack-plugin).
Project is based on ES6 syntax. It is converted to ES5 by using [BabelJS](https://babeljs.io) compiler with stage `1`,
this means that we can use extra features like `await` and `async`. For stylesheets we use [Sass](http://sass-lang.com/)
compiler.

### Installation

When you checkout the project for the first time, run `npm install`. This will install `npm`
dependencies as well as trigger `bower install` for installing bower dependencies.

### Testing

Project is tested with `karma` with `jasmine` framework and `eslint` for reporting on patterns. You can run tests and
pattern reporting by simply calling `npm run test`. Note that if `eslint` fails then tests will not be triggered.
You can also use `npm run autotest` for automated test runs when some source files changes.

#### Functional Testing

Project is tested with `jasmine` using `protractor` framework. First, you need to start webdriver-manager
by calling `npm run webdriver-start`. Then run `npm run dev -mock`. Finally run the tests by calling
`npm run protractor`. The tests will run in the different browsers(Chrome, Firefox, Safari and Internet Explorer).

### Build

Project is built by calling `npm run build`. This will create new source files and place them in `./build` directory,
and will also create Styleguide of the project. Mount them on any server and you are ready to go, or alternatively
use `npm run dev`.

### Dev Server

Start dev server by calling `npm run dev`, this will automatically mount a server on `3000` localhost port.
If any change is made during dev server run, source files will be automatically rebuilt.

### Prod Server

Start prod server by calling `npm run serve-prod`. This will try to mount `./build` directory to `80` port by
using [http-server](https://github.com/indexzero/http-server) for serving static resources. Before you run this make
sure you have called `npm run build` first.

### Styleguide

To create a styleguide of the project run `npm run styleguide`. This will
generate styleguide's files using [Kss-node](https://github.com/kss-node/kss-node)
in `/build/styleguide/` directory.

### Environment

Webpack is running with `webpack.EnvironmentPlugin`. This means that we can define custom build environments, for e.g.
if you insert `process.env.NODE_ENV` into your code it will be replaced with build environment `development` or `production`.
This way you can do custom actions which are only required for a specified environment.

### File Layout Structure

#### Source Files

Source files are stored in `./src` directory. When webpack start build it starts from `./src/index.js` file.
Library files from `npm` and `bower` can be accessed by simple imports like in `node.js`. We strongly suggest
to use `npm` dependencies and use `bower` for dependencies that are not published to `npm`.

```
├──  /src
│   ├── /layout
│   │   │   # overall site layout, navigation bar and components that are visible
│   │   │   # through out all views go here
│   ├── /modules
│   │   │   # place to put custom modules used all over the system
│   │   └── /<modules list...>
│   ├── /pages
│   │   │   # pages can have its controllers, factories, and other.
│   │   │   # Each page is a different angular module so it can
│   │   │   # have custom configuration and route. When using angular ui router you will
│   │   │   # define it states in page module route - not in the global one!
│   │   │   # this way you get a clear view on page dependencies.
│   │   └── /<pages list...>
│   │       ├── /controllers
│   │       ├── /factories
│   │       ├── /*-route.js
│   │       ├── /*-module.js
│   │       └── /*.html
│   ├── /styles
│   │   │   # place custom stylesheets used all over the system
│   │   └── /<stylesheet list...>
│   ├── /constants.js
│   ├── /routing.js
│   ├── /run.js
│   └── /index.js
```

##### Ng Annotate

Make sure that you use `"ngInject";` for angular injectable dependencies.
Please read [ES6 support for ngAnnotate](https://github.com/olov/ng-annotate#es6-and-typescript-support).

### Test Files

Test files are stored in `./test/specs` directory. When karma runner starts, it includes files by
`./test/specs/**/*.test.js` pattern only. Other files like source files that need to be tested have to be
imported in the test files.

### API

In order to get API host add `process.env.API_HOST`. This will return host if it is defined at build time.

### Swagger

Swagger config file is placed in `./swagger/api/swagger/swagger.yaml`

#### Swagger Mock Server

Start mock server by running `npm run serve-mock`. **Note** that if you want that your dev server would
use swagger mock server you need to start it with `--mock` argument, for e.g. `npm run dev --mock`. Mock
server runs in port 10010

#### Swagger Docs Editor

Start docs editor by running `npm run docs`.

#### Swagger CodeGen

Run `../common/./gen_server_stubs.sh [/output_directory]` to use the swagger-codegen tools to generate stubs from the swagger specification. If output not specified script will generate stubs into `../server` (https://github.com/swagger-api/swagger-codegen/blob/master/README.md)


#### Swagger UI

Runs on port 5000/docs alongside swagger mock server when running it `npm run serve-mock`.

### Jersey API

To run the jersey api from the client directory use the npm task `start-server` or from the server directory `../server mvn -f build.xml clean generate-sources jetty:run`

#### Coverage report on jersey

With the server running and from the server directory, run `mvn -f build.xml site` to generate the coverage reporter for the jersey API. Optionally from
the client directory run the npm task `server-coverage`. This will generate a html file under `/server/target/site/index.html`
