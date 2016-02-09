var SwaggerExpress = require('swagger-express-mw');
var app = require('express')();
var assert = require('assert');

var conn = require('connect')();
var http = require('http');
var cookieParser = require('cookie-parser');
var SwaggerUi = require('swagger-tools/middleware/swagger-ui');

var port = process.env.PORT || 10010;

SwaggerExpress.create({
  appRoot: __dirname
}, function (err, swaggerExpress) {
  if (err) throw err;

  swaggerExpress.runner.config.swagger.securityHandlers = {
    sessionToken: function (req, authOrSecDef, scopesOrApiKey, callback) {
      function UnauthorizedError() {
        this.code = 'Unauthorized';
        this.message = 'You are not authorized to perform this request.';
        this.statusCode = 401;
        this.headers = [];
      }

      UnauthorizedError.prototype.toString = function () {
        return this.code + ': ' + this.message;
      }

      if (req.cookies.sessionToken) {
        callback();
      }
      else {
        callback(new UnauthorizedError());
      }
    }
  };

  swaggerExpress.sysConfig.corsOptions = {
    origin: true,
    methods: ['GET', 'PUT', 'POST', 'OPTIONS', 'DELETE'],
    credentials: true,
  };

  app.use(swaggerExpress.cors());
  app.use(cookieParser());
  app.use(SwaggerUi(swaggerExpress.runner.swagger));

  swaggerExpress.register(app);
  app.listen(port);

  console.log('Server is started at http://localhost:%d', port);
  console.log('SwaggerUi running at http://localhost:%d/docs', port);
});
