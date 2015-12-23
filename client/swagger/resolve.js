var resolve = require('json-refs').resolveRefs;
var YAML = require('yaml-js');
var fs = require('fs');

var JSON2YAML = require('json2yaml'), json, data, yml;

var root = YAML.load(fs.readFileSync('./swagger/api/swagger/' + process.argv[3]).toString());
var options = {
  processContent: function (content) {
    return YAML.load(content);
  },
  resolveLocalRefs: false
};

resolve(root, options).then(function (results) {
  console.log('Successfully parsed ' + process.argv[3]);
  var yamlOutput = './swagger/api/swagger/swagger.yaml';
  fs.writeFile(yamlOutput, JSON2YAML.stringify(results.resolved, null, 2), _errorHandler);
  function _errorHandler(err) {
    if (err) return console.log(err);
  }
});

