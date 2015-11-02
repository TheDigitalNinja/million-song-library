require('babel/register');

exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['test/e2e/**/*.js'],
  baseUrl: 'http://localhost:3000/#',
  multiCapabilities: [{
    browserName: 'firefox'
  }, {
    browserName: 'internet explorer'
  }, {
    browserName: 'chrome'
  }],
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 50000,
    isVerbose: true,
  },
}
