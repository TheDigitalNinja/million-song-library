/* global describe, it, expect, beforeEach, pending */
describe('artistPage', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/artists/1');
  });

  it('checks artist name', () => {
    const albumName = browser.driver.findElement(By.className('artist-name')).getText();
    expect(albumName).toBe('La 12');
  });

  describe('nav in artist page', () => {
    describe('Songs', () => {
      it('checks song name', () => {
        browser.driver.findElement(By.linkText('Songs')).click();
        const songName = browser.driver.findElement(By.linkText('Liga Campeon')).getText();
        expect(songName).toBe('Liga Campeon');
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
      it('checks album name', () => {
        browser.driver.findElement(By.linkText('Albums')).click();
        const albumName = browser.driver.findElement(By.linkText('Some album')).getText();
        expect(albumName).toBe('Some album');
      });

      it('redirects to album page', () => {
        browser.driver.findElement(By.linkText('Albums')).click();
        browser.driver.findElement(By.linkText('Some album')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe('http://localhost:3000/#/album/1');
          debugger;
        });
      });
    });

    describe('Similar Artists', () => {
      it('checks artist name', () => {
        browser.driver.findElement(By.linkText('Similar artists')).click();
        const albumName = browser.driver.findElement(By.linkText('Mercyful Fate')).getText();
        expect(albumName).toBe('Mercyful Fate');
      });

      it('redirects to artist page', () => {
        browser.driver.findElement(By.linkText('Similar artists')).click();
        browser.driver.findElement(By.linkText('Mercyful Fate')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe('http://localhost:3000/#/artists/2');
          debugger;
        });
      });
    });
  });
});
