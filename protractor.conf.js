exports.config = {
  framework: 'jasmine',
  seleniumAddress: 'http://localhost:4444/wd/hub',
  specs: ['./test/e2e/**/*.js'],
  multiCapabilities: [{
    browserName: 'firefox'
  }, {
    browserName: 'safari'
  }, {
    browserName: 'internet explorer'
  }, {
    browserName: 'chrome'
  }]
}
