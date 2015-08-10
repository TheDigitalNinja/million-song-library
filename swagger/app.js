var SwaggerExpress = require("swagger-express-mw");
var app = require("express")();
var assert = require("assert");
var port = process.env.PORT || 10010;

SwaggerExpress.create({
  appRoot: __dirname
}, function (err, swaggerExpress) {
  if (err) throw err;

  swaggerExpress.runner.config.swagger.securityHandlers = {
    session_id: function (req, authOrSecDef, scopesOrApiKey, callback) {
      assert.notEqual(req.headers.sessionid, null);
      callback();
    }
  };
  swaggerExpress.register(app);
  app.listen(port);

  console.log("Server is started at http://127.0.0.1:" + port);
});
