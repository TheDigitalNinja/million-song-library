describe('albumPage', () => {

  beforeEach(() => {
    browser.driver.get(`${ browser.baseUrl }/album/1`);
    browser.driver.sleep(600);
  });

  describe('checks album info', () => {
    it('checks album name', () => {
      const albumName = browser.driver.findElement(By.css('[data-pt-id=album-name]')).getText();
      expect(albumName).toMatch('Some album');
    });

    describe('checks artist info', () => {
      it('checks artist name', () => {
        const artistName = browser.driver.findElement(By.css('[data-pt-id=album-artist-name]')).getText();
        expect(artistName).toMatch('La 12');
      });

      it('redirects to artist page', () => {
        browser.driver.findElement(By.css('[data-pt-id=album-artist-name]')).click();
        browser.getCurrentUrl().then((url) => {
          expect(url).toBe(`${ browser.baseUrl }/artists/1`);
          debugger;
        });
      });
    });

    it('checks genre name', () => {
      const genreName = browser.driver.findElement(By.css('[data-pt-id=album-genre]')).getText();
      expect(genreName).toMatch('Jazz');
    });

    it('checks album year', () => {
      const albumYear = browser.driver.findElement(By.css('[data-pt-id=album-year]')).getText();
      expect(albumYear).toMatch('2010');
    });
  });
});
