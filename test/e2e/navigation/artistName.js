/* global describe, it, expect, beforeEach, pending */
describe('artist name in home page', function () {

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click artist name', function () {
    pending('Wait for functionality');
    browser.driver.findElement(By.linkText("Artist Name")).click();
    expect(browser.driver.getCurrentUrl()).toMatch('http://localhost:3000/#/artist/artistId');
  });
});
