var SwaggerExpress = require("swagger-express-mw");
var app = require("express")();
var assert = require("assert");

var conn = require('connect')();
var http = require('http');
var swaggerTools = require('swagger-tools');
var cookieParser = require('cookie-parser');

var port = process.env.PORT || 10010;
var swaggerUiPort = 5000;

SwaggerExpress.create({
  appRoot: __dirname
}, function (err, swaggerExpress) {
  if (err) throw err;

  swaggerExpress.runner.config.swagger.securityHandlers = {
    sessionToken: function (req, authOrSecDef, scopesOrApiKey, callback) {
      function UnauthorizedError() {
        this.code = "Unauthorized";
        this.message = "You are not authorized to perform this request.";
        this.statusCode = 401;
        this.headers = [];
      }
      UnauthorizedError.prototype.toString = function() {
        return this.code + ": " + this.message;
      }

      if(req.cookies.sessionToken) {
        callback();
      }
      else {
        callback(new UnauthorizedError());
      }
    }
  };

  swaggerExpress.sysConfig.corsOptions = {
    origin: true,
    methods: ['GET', 'PUT', 'POST', 'OPTIONS'],
    credentials: true,
  };

  app.use(swaggerExpress.cors());
  app.use(cookieParser());

  swaggerExpress.register(app);
  app.listen(port);

  console.log("Server is started at http://127.0.0.1:" + port);
});


// swaggerRouter configuration
var options = {
  swaggerUi: './swagger.json',
  controllers: './api/mocks',
  useStubs: process.env.NODE_ENV === 'development' ? true : false // Conditionally turn on stubs (mock mode)
};

// The Swagger document (require it, build it programatically, fetch it from a URL, ...)
var swaggerDoc = require('./api/swagger/swagger.json');

// Initialize the Swagger middleware
swaggerTools.initializeMiddleware(swaggerDoc, function (middleware) {
  // Interpret Swagger resources and attach metadata to request - must be first in swagger-tools middleware chain
  conn.use(middleware.swaggerMetadata());

  // Validate Swagger requests
  conn.use(middleware.swaggerValidator());

  // Route validated requests to appropriate controller
  conn.use(middleware.swaggerRouter(options));

  // Serve the Swagger documents and Swagger UI
  conn.use(middleware.swaggerUi());

  // Start the server
  http.createServer(conn).listen(swaggerUiPort, function () {
    console.log('Your server is listening on port %d (http://localhost:%d)', port, port);
    console.log('Swagger-ui is available on http://localhost:%d/docs', swaggerUiPort);
  });
});
