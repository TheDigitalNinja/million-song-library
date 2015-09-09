/* global describe, it, expect, beforeEach, pending */
describe('play button in home page', function () {
  var homeURL;

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click play button', function () {
    pending('Wait for functionality');
    homeURL = browser.driver.getCurrentUrl();
    browser.driver.findElement(By.linkText("Play")).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });
});
