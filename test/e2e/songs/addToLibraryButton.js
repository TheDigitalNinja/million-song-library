/* global describe, it, expect, beforeEach, pending */
describe('add to library button in home page', function () {
  var homeURL;

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click add to library button', function () {
    pending('Wait for functionality');
    var homeURL = browser.driver.getCurrentUrl();
    browser.driver.findElement(By.linkText("Add to library")).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });
});
