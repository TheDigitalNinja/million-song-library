/* global describe, it, expect, beforeEach, pending */
describe('sonPage', () => {

  beforeEach(() => {
    browser.driver.get('http://localhost:3000/#/song/1');
  });

  describe('checks song info', () => {
    it('checks song name', () => {
      const songName = browser.driver.findElement(By.className('song-name')).getText();
      expect(songName).toMatch('Liga Campeon');
    });

    it('checks artist name', () => {
      const artistName = browser.driver.findElement(By.linkText('La 12')).getText();
      expect(artistName).toMatch('La 12');
    });

    it('checks genre name', () => {
      const genreName = browser.driver.findElement(By.className('genre-name')).getText();
      expect(genreName).toMatch('Classic');
    });

    it('checks song release', () => {
      const songRelease = browser.driver.findElement(By.className('song-release')).getText();
      expect(songRelease).toMatch('01-01-1919');
    });
  });

});
