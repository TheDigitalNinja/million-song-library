/* global describe, it, expect, beforeEach, pending */
describe('error page', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/errorPage');
  });

  it('Check error message', () => {
    var text = browser.driver.findElement(By.className('page-header')).getText();
    expect(text).toMatch('That page can’t be found.');
  });

  it('Redirect to homepage', () => {
    var homeURL = 'http://localhost:3000/#';
    browser.driver.findElement(By.linkText("home page")).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });

});
