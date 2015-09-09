/* global describe, it, expect, beforeEach, pending */
describe('genre name in home page', function () {

  beforeEach(function() {
    browser.driver.get('http://localhost:3000/#/');
  });

  xit('when click genre name', function () {
    pending('Wait for functionality');
    browser.driver.findElement(By.linkText("Pop")).click();
    expect(browser.driver.getCurrentUrl()).toMatch('http://localhost:3000/#/genre/pop');
  });
});
