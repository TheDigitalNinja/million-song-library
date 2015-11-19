describe('songPage', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/song/1`);
    browser.driver.sleep(600);
  });

  describe('checks song info', () => {
    it('checks song name', () => {
      const songName = browser.driver.findElement(By.css('[data-pt-id=song-name]')).getText();
      expect(songName).toMatch('Liga Campeon');
    });

    describe('checks artist info', () => {
      it('checks artist name', () => {
        const artistName = browser.driver.findElement(By.css('[data-pt-id=song-artist-name]')).getText();
        expect(artistName).toMatch('La 12');
      });

      it('redirects to artist page', () => {
        browser.driver.findElement(By.css('[data-pt-id=song-artist-name]')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe(`${ browser.baseUrl }/artists/1`);
          debugger;
        });
      });
    });

    describe('checks album info', () => {
      it('checks album name', () => {
        const artistName = browser.driver.findElement(By.css('[data-pt-id=song-album-name]')).getText();
        expect(artistName).toMatch('Some album');
      });
      it('redirects to album page', () => {
        browser.driver.findElement(By.css('[data-pt-id=song-album-name]')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe(`${ browser.baseUrl }/album/1`);
          debugger;
        });
      });

    });

    it('checks genre name', () => {
      const genreName = browser.driver.findElement(By.css('[data-pt-id=song-genre]')).getText();
      expect(genreName).toMatch('Classic');
    });

    it('checks song release', () => {
      const songRelease = browser.driver.findElement(By.css('[data-pt-id=song-year]')).getText();
      expect(songRelease).toMatch('1919');
    });
  });

  describe('checks similar artist', () => {
    it('checks similar artist name', () => {
      browser.driver.sleep(600);
      const artistName = browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).getText();
      expect(artistName).toMatch('Mercyful Fate');
    });

    it('redirects to artist page', () => {
      browser.driver.sleep(600);
      browser.driver.findElement(By.css('artists-list artist-box div.media-box-meta a')).click();
      browser.getCurrentUrl().then((url) => {
        expect(url).toBe(`${ browser.baseUrl }/artists/2`);
        debugger;
      });
    });
  });
});
