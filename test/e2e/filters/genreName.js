/* global describe, it, expect, beforeEach, pending */
describe('genre filter in home page', () => {

  xit('check the catalog name', () => {
    browser.driver.get('http://localhost:3000/#/');
    browser.driver.findElement(By.linkText('Rock')).click();
    const text = browser.driver.findElement(By.className('col-xs-12')).getText();
    expect(text).toMatch('rock Catalog');
  });

  xit('check genre name', () => {
    const text = browser.driver.findElement(By.className('genre')).getText();
    expect(text).toMatch('Rock');
  });

});
