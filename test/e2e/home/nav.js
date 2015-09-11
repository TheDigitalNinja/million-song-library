/* global describe, it, expect, beforeEach, pending */
describe('nav', function () {

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/');
  });

  it('Home', function () {
    var homeURL = browser.driver.getCurrentUrl();
    browser.driver.findElement(By.linkText("Home")).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });

  it('Artist', function () {
    browser.driver.findElement(By.linkText("Artist")).click();
    browser.getCurrentUrl().then(function (url) {
      expect(url).toBe("http://localhost:3000/#/artist/artistId");
      debugger;
    });
  });

  it('Song', function () {
    browser.driver.findElement(By.linkText("Song")).click();
    browser.getCurrentUrl().then(function (url) {
      expect(url).toBe("http://localhost:3000/#/song/songId");
      debugger;
    });
  });

});
