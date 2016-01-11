var resolve = require('json-refs').resolveRefs;
var YAML = require('yaml-js');
var fs = require('fs');

var JSON2YAML = require('json2yaml'), json, data, yml;

var swaggerDir = './swagger/api/swagger/';

var inputFile = process.argv[3];
var tmpFile = 'swagger_tmp.yaml';
var outputFile = 'swagger.yaml';

var options = {
  processContent: function (content) {
    return YAML.load(content);
  },
  resolveLocalRefs: false
};


// GENERATE GLOBAL PATHS FILE FOR GLOBAL SWAGGER DEFINITION
if(inputFile == 'index.yaml') {
  var accountEdgePaths = fs.readFileSync(swaggerDir + 'account-edge/paths.yaml').toString();
  var catalogEdgePaths = fs.readFileSync(swaggerDir + 'catalog-edge/paths.yaml').toString();
  var ratingsEdgePaths = fs.readFileSync(swaggerDir + 'ratings-edge/paths.yaml').toString();
  var loginEdgePaths = fs.readFileSync(swaggerDir + 'login-edge/paths.yaml').toString();
  var searchEdgePaths = fs.readFileSync(swaggerDir + 'search-edge/paths.yaml').toString();
  var playerEdgePaths = fs.readFileSync(swaggerDir + 'player-edge/paths.yaml').toString();

  var paths = accountEdgePaths + catalogEdgePaths + ratingsEdgePaths + loginEdgePaths + searchEdgePaths + playerEdgePaths;

  var pathsOutput = swaggerDir + 'paths.yaml';
  fs.writeFile(pathsOutput, paths, _errorHandler);
}

//RESOLVE NESTED REFERENCES
function callback(file) {
  runParser(YAML.load(file), outputFile, true, null);
}

var root = YAML.load(fs.readFileSync(swaggerDir + inputFile).toString());

runParser(root, tmpFile, false, callback);

function runParser(root, outputFile, write, callback) {

  resolve(root, options).then(function (results) {

    var resolvedFile = JSON2YAML.stringify(results.resolved, null, 2);

    if (write) {
      var yamlOutput = swaggerDir + outputFile;
      fs.writeFile(yamlOutput, resolvedFile, _errorHandler);
      console.log('Successfully parsed ' + inputFile + ' into file: ' + outputFile);
    }

    if (callback) {
      callback(resolvedFile);
    }
  });
}

function _errorHandler(err) {
  if (err) return console.log(err);
}
