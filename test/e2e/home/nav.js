/* global describe, it, expect, beforeEach, pending */
describe('nav', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/');
  });

  it('Home', () => {
    const homeURL = browser.driver.getCurrentUrl();

    browser.driver.findElement(By.linkText('Home')).click();
    expect(browser.driver.getCurrentUrl()).toMatch(homeURL);
  });

  it('Artist', () => {
    browser.driver.findElement(By.linkText('Artist')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe('http://localhost:3000/#/artist/artistId');
      debugger;
    });
  });

  it('Song', () => {
    browser.driver.findElement(By.linkText('Song')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe('http://localhost:3000/#/song/songId');
      debugger;
    });
  });

});
