/* global describe, it, expect, beforeEach, pending */
describe('genre name in home page', () => {

  it('when click genre name', () => {
    browser.driver.get('http://localhost:3000/#/');
    browser.driver.findElement(By.linkText("Rock")).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe("http://localhost:3000/#/genres/rock");
      debugger;
    });
  });

  it('check the catalog name', () => {
    var text = browser.driver.findElement(By.className('col-xs-12')).getText();
    expect(text).toMatch('rock Catalog');
  });

  it('check genre name', () => {
    var text = browser.driver.findElement(By.className('genre')).getText();
    expect(text).toMatch('Rock');
  });

  describe('sub-genre in rock page', () => {
    it('when click metal in rock page', () => {
      browser.driver.get('http://localhost:3000/#/genres/rock');
      browser.driver.findElement(By.linkText("Metal")).click();
      browser.getCurrentUrl().then((url) => {
        expect(url).toBe("http://localhost:3000/#/genres/metal");
        debugger;
      });
    });
  });
});
