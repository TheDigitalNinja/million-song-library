/* global describe, it, expect, beforeEach, pending */
describe('albumPage', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/album/1');
  });

  describe('checks album info', () => {
    it('checks album name', () => {
      const albumName = browser.driver.findElement(By.className('album-name')).getText();
      expect(albumName).toMatch('Some album');
    });

    it('checks artist name', () => {
      const artistName = browser.driver.findElement(By.linkText('La 12')).getText();
      expect(artistName).toMatch('La 12');
    });

    it('checks genre name', () => {
      const genreName = browser.driver.findElement(By.className('genre-name')).getText();
      expect(genreName).toMatch('Jazz');
    });

    it('checks album year', () => {
      const albumYear = browser.driver.findElement(By.className('album-year')).getText();
      expect(albumYear).toMatch('2010');
    });
  });

});
