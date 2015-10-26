/* global describe, it, expect, beforeEach, pending */
describe('artistPage', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/artists/1');
  });

  it('checks artist name', () => {
    const albumName = browser.driver.findElement(By.className('artist-name')).getText();
    expect(albumName).toMatch('La 12');
  });

  describe('nav in artist page', () => {
    describe('Songs', () => {
      it('checks song name', () => {
        browser.driver.findElement(By.linkText('Songs')).click();
        const songName = browser.driver.findElement(By.linkText('Liga Campeon')).getText();
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

    describe('Albums', () => {
      xit('checks album name', () => {
        browser.driver.findElement(By.linkText('Albums')).click();
        const albumName = browser.driver.findElement(By.linkText('Some album')).getText();
        expect(albumName).toMatch('Some album');
      });

      xit('redirects to album page', () => {
        browser.driver.findElement(By.linkText('Albums')).click();
        browser.driver.findElement(By.linkText('Some album')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe('http://localhost:3000/#/album/1');
          debugger;
        });
      });
    });
  });

});
