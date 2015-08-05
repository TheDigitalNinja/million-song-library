var SwaggerExpress = require("swagger-express-mw");
var app = require("express")();
var port = process.env.PORT || 10010;

SwaggerExpress.create({
  appRoot: __dirname
}, function (err, swaggerExpress) {
  if (err) throw err;

  swaggerExpress.register(app);
  app.listen(port);

  console.log("Server is started at http://127.0.0.1:" + port);
});
