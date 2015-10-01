/* global describe, it, expect, beforeEach, pending */
describe('nav', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/');
  });

  it('Artists', () => {
    browser.driver.findElement(By.linkText('Artists')).click();
    const artistName = browser.driver.findElement(By.linkText('La 12')).getText();
    expect(artistName).toMatch('La 12');
  });

  it('redirects to artist page', () => {
    browser.driver.findElement(By.linkText('Artists')).click();
    browser.driver.findElement(By.linkText('La 12')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe('http://localhost:3000/#/artists/1');
      debugger;
    });
  });

  it('Albums', () => {
    browser.driver.findElement(By.linkText('Albums')).click();
    const albumName = browser.driver.findElement(By.linkText('Some album')).getText();
    expect(albumName).toMatch('Some album');
  });

  it('redirects to album page', () => {
    browser.driver.findElement(By.linkText('Albums')).click();
    browser.driver.findElement(By.linkText('Some album')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe('http://localhost:3000/#/album/1');
      debugger;
    });
  });

  it('Songs', () => {
    browser.driver.findElement(By.linkText('Songs')).click();
    var songName = browser.driver.findElement(By.linkText('Liga Campeon')).getText();
    expect(songName).toMatch('Liga Campeon');
  });

  it('redirects to song page', () => {
    browser.driver.findElement(By.linkText('Songs')).click();
    browser.driver.findElement(By.linkText('Liga Campeon')).click();
    browser.getCurrentUrl().then((url) => {
      expect(url).toBe('http://localhost:3000/#/song/1');
      debugger;
    });
  });

});
